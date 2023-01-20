package com.example.tp_unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp_unsplash.adapters.UnSplashLikedPhotoAdapter
import com.example.tp_unsplash.api.UnSplashRetrofit
import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.databinding.ActivityLikedPhotoViewBinding
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.viewmodels.UnSplashViewModel

class LikedPhotoViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikedPhotoViewBinding
    private lateinit var viewModel: UnSplashViewModel
    private lateinit var adapter: UnSplashLikedPhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service)
        viewModel = UnSplashViewModel(repository)
        adapter = UnSplashLikedPhotoAdapter(listOf(), this, viewModel)
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerView.adapter = adapter
        addObservers()
        // valeur en dur pour le moment <- TODO
        viewModel.fetchLikedPhotos("knockace")
    }

    private fun addObservers(){
        viewModel.liked_photos.observe(this) {
            adapter.setData(it)
        }
    }
}