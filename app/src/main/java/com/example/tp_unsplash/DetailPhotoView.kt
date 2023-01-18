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
import com.example.tp_unsplash.viewmodels.UnSplashViewModel

class DetailPhotoView : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPhotoViewBinding
    private lateinit var viewModel: UnSplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service)
        viewModel = UnSplashViewModel(repository)
    }


    override fun onStart() {
        super.onStart()
        // Set data from intent to the view
        val photo_uri : Uri = Uri.parse(intent.getStringExtra("photo_url"))
        binding.photoSmall.load(photo_uri)
        val author_name  = intent.getStringExtra("author_name")
        binding.txtAuthorName.text = author_name
        val is_liked = intent.getBooleanExtra("is_liked", false)
        val description = intent.getStringExtra("description")
        println(description)
        binding.txtDescription.text = description
        if(is_liked) {
            // If arleady like we want to un-like it
            binding.btnLike.setBackgroundResource(R.drawable.btn_unlike_style)

        } else {
            // If not like we want to like it
            binding.btnLike.setBackgroundResource(R.drawable.btn_like_style)
        }
        // Set the listener for the like button
        binding.btnLike.setOnClickListener {
            //Toast(this, "Like button clicked", Toast.LENGTH_SHORT).show()
            val photoId = intent.getStringExtra("photo_id")
            println("Like button clicked we will update the photo with id: $photoId")
            if(is_liked) {
                // If arleady like we want to un-like it
                binding.btnLike.setBackgroundResource(R.drawable.btn_unlike_style)
                viewModel.unlikePhoto(photoId!!)
            } else {
                // If not like we want to like it
                binding.btnLike.setBackgroundResource(R.drawable.btn_like_style)
                viewModel.likePhoto(photoId!!)
            }
        }
    }
}
