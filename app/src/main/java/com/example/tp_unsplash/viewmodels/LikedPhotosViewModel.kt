package com.example.tp_unsplash.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.schemas.UnSplashPhoto
import kotlinx.coroutines.launch

class LikedPhotosViewModel(private val repository: UnSplashRepository) : ViewModel() {
    private var _liked_photos: MutableLiveData<List<UnSplashPhoto>> = MutableLiveData()
    var liked_photos: MutableLiveData<List<UnSplashPhoto>> = _liked_photos

    fun fetchLikedPhotos(username: String) {
        viewModelScope.launch {
            _liked_photos.value = repository.get_liked_photos(username)
        }
    }

    fun likePhoto(id: String) {
        viewModelScope.launch {
            repository.like_photo(id)
        }
    }

    fun unlikePhoto(id: String) {
        viewModelScope.launch {
            repository.unlike_photo(id)
        }
    }
}

class LikedPhotosViewModelFactory(private val repository: UnSplashRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikedPhotosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnSplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}