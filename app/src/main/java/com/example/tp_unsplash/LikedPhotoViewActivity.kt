package com.example.tp_unsplash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.tp_unsplash.adapters.UnSplashLikedPhotoAdapter
import com.example.tp_unsplash.api.UnSplashRetrofit
import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.databinding.ActivityLikedPhotoViewBinding
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val USERNAME = "knockace"
class LikedPhotoViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikedPhotoViewBinding
    private lateinit var viewModel: LikedPhotosViewModel
    private lateinit var adapter: UnSplashLikedPhotoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPhotoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Service for api calls
        val service: UnSplashRetrofitService = UnSplashRetrofit.getService()
        // database
        val database = UnSplashRoomDatabase.getDatabase(this)
        // Dao for database
        val dao = database.getLikedPhotoDao()
        // Repository that will be used by the view model
        val repository = UnSplashRepository(service, dao)
        viewModel = LikedPhotosViewModel(repository)
        adapter = UnSplashLikedPhotoAdapter(listOf(), viewModel)
        viewModel.initLikedPhotos(USERNAME)
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerView.adapter = adapter
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        addObservers()
        // valeur en dur pour le moment <- TODO
        viewModel.fetchLikedPhotos("knockace")
        val nav : BottomNavigationView = binding.navMenu.navView
        setUpMenu(nav)
    }

    private fun addObservers(){
        viewModel.liked_photos.observe(this) {
            adapter.setData(it)
        }
    }

    fun setUpMenu(navMenu: BottomNavigationView){
        // Set selected item
        navMenu.selectedItemId = R.id.action_liked_photos;
        // Switch between activities
        navMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    //startActivity(Intent(this, MainActivity::class.java))
                    // Get current position in recycler view
                    val position = (binding.recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findFirstVisibleItemPosition()
                    Log.d("position", position.toString())
                    // TODO : use this to like instead ???
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

    fun test(){
        println("test")

    }
}