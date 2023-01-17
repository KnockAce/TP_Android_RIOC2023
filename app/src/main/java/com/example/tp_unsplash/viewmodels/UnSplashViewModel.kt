package com.example.tp_unsplash.viewmodels

import androidx.lifecycle.*
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.schemas.UnSplashPhoto
import kotlinx.coroutines.launch

class UnSplashViewModel(private val repository: UnSplashRepository) : ViewModel() {
    private var _photos: MutableLiveData<List<UnSplashPhoto>> = MutableLiveData()
    var photos: LiveData<List<UnSplashPhoto>> = _photos

    fun fetchPhotos() {
        viewModelScope.launch {
            _photos.value = repository.get_random_photo()
        }
    }

    fun getAllPhotos(): LiveData<List<UnSplashPhoto>> {
        return photos
    }

    /*
    fun getPhoto(id: String): UnSplashPhoto {
        // todo
        return UnSplashPhoto()
    }*/
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

