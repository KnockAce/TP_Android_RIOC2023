package com.example.tp_unsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp_unsplash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        // Random photo
        binding.btnWelcome.setOnClickListener {
            val intent = Intent(this, PhotoViewActivity::class.java)
            startActivity(intent)
        }
        // Search photo
        binding.btnGoLikedPhotos.setOnClickListener {
            val intent = Intent(this, LikedPhotoViewActivity::class.java)
            startActivity(intent)
        }
    }
}