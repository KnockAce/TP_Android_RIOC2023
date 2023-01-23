package com.example.tp_unsplash

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.example.tp_unsplash.api.UnSplashRetrofit
import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.databinding.ActivityDetailPhotoViewBinding
import com.example.tp_unsplash.repository.UnSplashRepository
import android.util.Log
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModel

class DetailPhotoView : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPhotoViewBinding
    private lateinit var viewModel: LikedPhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // database
        val database = UnSplashRoomDatabase.getDatabase(this)
        // Dao for database
        val dao = database.getLikedPhotoDao()
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service, dao)
        viewModel = LikedPhotosViewModel(repository)
    }


    override fun onStart() {
        super.onStart()
        // Set data from intent to the view
        val photo_uri : Uri = Uri.parse(intent.getStringExtra("photo_url"))
        binding.photoSmall.load(photo_uri)
        val author_name  = intent.getStringExtra("author_name")
        "Author: $author_name".also { binding.txtAuthorName.text = it }
        var is_liked = intent.getBooleanExtra("is_liked", false)
        val description = intent.getStringExtra("description")
        "Description : $description".also { binding.txtDescription.text = it }
        // Apply style to btn
        if(is_liked) {
            // If arleady like we want to un-like it
            binding.btnLike.setBackgroundResource(R.drawable.btn_unlike_style)

        } else {
            // If not like we want to like it
            binding.btnLike.setBackgroundResource(R.drawable.btn_like_style)
        }
        // Set the listener for the like button
        binding.btnLike.setOnClickListener {
            val photoId = intent.getStringExtra("photo_id")
            println("Like button clicked we will update the photo with id: $photoId")
            if(is_liked) {
                Log.d("Like button", "We will unlike the photo")
                // If arleady like we want to un-like it
                binding.btnLike.setBackgroundResource(R.drawable.btn_unlike_style)
                viewModel.unlikePhoto(photoId!!)
                is_liked = false
                Log.i("Like button", "We unliked the photo successfully")
                Toast.makeText(this, "We unliked the photo successfully", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Like button", "We will like the photo")
                // If not like we want to like it
                binding.btnLike.setBackgroundResource(R.drawable.btn_like_style)
                viewModel.likePhoto(photoId!!)
                is_liked = true
                Log.i("Like button", "We liked the photo successfully")
                Toast.makeText(this, "We liked the photo successfully", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
