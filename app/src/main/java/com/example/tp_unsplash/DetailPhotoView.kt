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
import androidx.lifecycle.ViewModelProvider
import com.example.tp_unsplash.schemas.UnSplashPhoto
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModel
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModelFactory

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
        viewModel = ViewModelProvider(this, LikedPhotosViewModelFactory(repository))[LikedPhotosViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        // Set data from intent to the view
        val photo = intent.getSerializableExtra("photo") as UnSplashPhoto
        // Load image with coil
        val photo_uri : Uri = Uri.parse(photo.urls.full)
        binding.photoSmall.load(photo_uri)
        "Author: ${photo.user.name}".also { binding.txtAuthorName.text = it }
        var isLiked = photo.liked_by_user
        "Description : ${photo.alt_description}".also { binding.txtDescription.text = it }
        "Likes : ${photo.likes}".also { binding.txtNbLikes.text = it }
        // Apply style to like btn
        if(isLiked) {
            // If arleady like we want to un-like it
            binding.btnLike.setBackgroundResource(R.drawable.btn_unlike_style)
        } else {
            // If not like we want to like it
            binding.btnLike.setBackgroundResource(R.drawable.btn_like_style)
        }

        // Set the listener for the like button
        binding.btnLike.setOnClickListener {
            val photoId = photo.id
            println("Like button clicked we will update the photo with id: $photoId")
            if(isLiked) {
                Log.d("Like button", "We will unlike the photo")
                // If arleady like we want to un-like it
                binding.btnLike.setBackgroundResource(R.drawable.btn_unlike_style)
                viewModel.unlikePhoto(photoId)
                isLiked = false
                // Remove like to txtNbLikes
                "Likes : " + (photo.likes - 1).toString().also { binding.txtNbLikes.text = it }
                Log.i("Like button", "We unliked the photo successfully")
                Toast.makeText(this, "We unliked the photo successfully", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Like button", "We will like the photo")
                // If not like we want to like it
                binding.btnLike.setBackgroundResource(R.drawable.btn_like_style)
                viewModel.likePhoto(photoId)
                isLiked = true
                // Add like to txtNbLikes
                "Likes : " + (photo.likes + 1).toString().also { binding.txtNbLikes.text = it }
                Log.i("Like button", "We liked the photo successfully")
                Toast.makeText(this, "We liked the photo successfully", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
