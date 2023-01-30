package com.example.tp_unsplash.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.schemas.UnSplashPhoto
import kotlinx.coroutines.launch

class SearchPhotosViewModel(private val repository: UnSplashRepository) : ViewModel(){
    private var _photos: MutableLiveData<List<UnSplashPhoto>> = MutableLiveData()
    var photos: MutableLiveData<List<UnSplashPhoto>> = _photos

    fun fetchPhotos(search: String) {
        viewModelScope.launch {
            _photos.value = repository.searchPhotos(search)
        }
    }
}

class SearchPhotosModelFactory(private val repository: UnSplashRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchPhotosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchPhotosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}