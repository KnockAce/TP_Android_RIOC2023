package com.example.tp_unsplash

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

class PhotoViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoViewBinding
    private lateinit var viewModel: UnSplashViewModel
    private lateinit var adapter: UnSplashPhotoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service)
        adapter = UnSplashPhotoAdapter(listOf())
        viewModel = ViewModelProvider(this, UnSplashViewModelFactory(repository)).get(UnSplashViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        binding.PhotoViews.adapter = adapter
        addObservers()

        viewModel.fetchPhotos()
    }

    private fun addObservers(){
        viewModel.photos.observe(this) {
            adapter.setData(it)
        }
    }
}