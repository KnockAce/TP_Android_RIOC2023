package com.example.tp_unsplash

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tp_unsplash.adapters.UnSplashPhotoAdapter
import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.databinding.ActivityPhotoViewBinding
import com.example.tp_unsplash.viewmodels.UnSplashViewModel
import com.example.tp_unsplash.viewmodels.UnSplashViewModelFactory
import com.example.tp_unsplash.api.UnSplashRetrofit
import com.example.tp_unsplash.repository.UnSplashRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class PhotoViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoViewBinding
    private lateinit var viewModel: UnSplashViewModel
    private lateinit var adapter: UnSplashPhotoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // database
        val database = UnSplashRoomDatabase.getDatabase(this)
        // Dao for database
        val dao = database.getLikedPhotoDao()
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service, dao)
        adapter = UnSplashPhotoAdapter(listOf())
        viewModel = ViewModelProvider(this, UnSplashViewModelFactory(repository)).get(UnSplashViewModel::class.java)
        binding.PhotoViews.adapter = adapter
        addObservers()

        viewModel.fetchPhotos()

        val nav : BottomNavigationView = binding.navMenu.navView
        setUpMenu(nav)
    }

    private fun addObservers(){
        viewModel.photos.observe(this) {
            adapter.setData(it)
        }
    }

    fun setUpMenu(navMenu: BottomNavigationView){
        // Set selected item
        navMenu.selectedItemId = R.id.action_random_photos;
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