package com.example.tp_unsplash

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.example.tp_unsplash.databinding.ActivityDetailPhotoViewBinding

class DetailPhotoView : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPhotoViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.btnLike.setOnClickListener {
            //Toast(this, "Like button clicked", Toast.LENGTH_SHORT).show()
            println("Like button clicked")
        }
    }
}