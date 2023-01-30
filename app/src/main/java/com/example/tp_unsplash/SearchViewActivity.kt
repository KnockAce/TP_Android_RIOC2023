package com.example.tp_unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tp_unsplash.adapters.UnSplashPhotoAdapter
import com.example.tp_unsplash.api.UnSplashRetrofit
import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.databinding.ActivitySearchViewBinding
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.viewmodels.SearchPhotosModelFactory
import com.example.tp_unsplash.viewmodels.SearchPhotosViewModel

class SearchViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchViewBinding
    private lateinit var viewModel: SearchPhotosViewModel
    private lateinit var adapter: UnSplashPhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // database
        val database = UnSplashRoomDatabase.getDatabase(this)
        // Dao for database
        val dao = database.getLikedPhotoDao()
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service, dao)
        viewModel = ViewModelProvider(this, SearchPhotosModelFactory(repository))[SearchPhotosViewModel::class.java]
        adapter = UnSplashPhotoAdapter(listOf())
        binding.recyclerView.adapter = adapter
        addObservers()

        val search = intent.getStringExtra("search")
        if (search != null) {
            viewModel.fetchPhotos(search)
        }


    }

    private fun addObservers(){
        viewModel.photos.observe(this) {
            adapter.setData(it)
        }
    }

}