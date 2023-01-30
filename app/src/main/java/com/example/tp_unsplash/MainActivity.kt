package com.example.tp_unsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp_unsplash.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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


        binding.btnSearchPhotos.setOnClickListener {
            val intent = Intent(this, SearchViewActivity::class.java)
            intent.putExtra("search", binding.editSearchInput.text.toString())
            startActivity(intent)
        }

        val nav : BottomNavigationView = binding.navMenu.navView
        setUpMenu(nav)

    }

    fun setUpMenu(navMenu: BottomNavigationView){
        // Set selected item
        navMenu.selectedItemId = R.id.action_home
        // Switch between activities
        navMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.action_liked_photos -> {
                    startActivity(Intent(this, LikedPhotoViewActivity::class.java))
                    true
                }
                R.id.action_random_photos -> {
                    println("random")
                    startActivity(Intent(this, PhotoViewActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

}