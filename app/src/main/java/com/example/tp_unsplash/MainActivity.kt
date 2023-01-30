package com.example.tp_unsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tp_unsplash.api.UnSplashRetrofit
import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.databinding.ActivityMainBinding
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModel
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val USERNAME = "knockace"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LikedPhotosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // database
        val database = UnSplashRoomDatabase.getDatabase(this)
        // Dao for database
        val dao = database.getLikedPhotoDao()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service, dao)
        viewModel = ViewModelProvider(this, LikedPhotosViewModelFactory(repository))[LikedPhotosViewModel::class.java]
        //viewModel.initLikedPhotos(USERNAME)
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