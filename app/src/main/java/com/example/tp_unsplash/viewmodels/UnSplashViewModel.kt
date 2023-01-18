package com.example.tp_unsplash.viewmodels

import androidx.lifecycle.*
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.schemas.UnSplashPhoto
import kotlinx.coroutines.launch

class UnSplashViewModel(private val repository: UnSplashRepository) : ViewModel() {
    private var _photos: MutableLiveData<List<UnSplashPhoto>> = MutableLiveData()
    var photos: LiveData<List<UnSplashPhoto>> = _photos
    private var _liked_photos: MutableLiveData<List<UnSplashPhoto>> = MutableLiveData()
    var liked_photos: LiveData<List<UnSplashPhoto>> = _liked_photos

    fun fetchPhotos() {
        viewModelScope.launch {
            _photos.value = repository.get_random_photo()
        }
    }

    fun getAllPhotos(): LiveData<List<UnSplashPhoto>> {
        return photos
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

    fun getLikedPhotos(username: String) {
        viewModelScope.launch {
            _liked_photos.value = repository.get_liked_photos(username)
        }
    }
}

class UnSplashViewModelFactory(private val repository: UnSplashRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UnSplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnSplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

