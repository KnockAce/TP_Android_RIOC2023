package com.example.tp_unsplash.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tp_unsplash.models.LikedPhotos
import com.example.tp_unsplash.repository.UnSplashRepository
import com.example.tp_unsplash.schemas.Links
import com.example.tp_unsplash.schemas.UnSplashPhoto
import com.example.tp_unsplash.schemas.UnSplashUser
import com.example.tp_unsplash.schemas.Urls
import kotlinx.coroutines.launch
import android.util.Log

class LikedPhotosViewModel(private val repository: UnSplashRepository) : ViewModel() {
    private var _liked_photos: MutableLiveData<List<UnSplashPhoto>> = MutableLiveData()
    var liked_photos: MutableLiveData<List<UnSplashPhoto>> = _liked_photos

    fun fetchLikedPhotos(username: String) {
        viewModelScope.launch {
            val likedPhotos = repository.getDbLikedPhotos()
            if (likedPhotos.isNotEmpty())
                // Convert to UnSplashPhoto object TODO : make it better ?
                _liked_photos.value = daoToUnSplashPhoto(likedPhotos)
            else
                _liked_photos.value = repository.getLikedPhotos(username)
        }
    }

    fun likePhoto(id: String) {
        viewModelScope.launch {
            // Add to local cache
            if(!repository.isInDb(id))
                repository.addPhotoToDb(id)
            repository.like_photo(id) // API call
        }
    }

    fun unlikePhoto(id: String) {
        viewModelScope.launch {
            Log.d("LikedPhotosViewModel", "We want to unlike $id")
            // Add to local cache
            if(repository.isInDb(id))
                Log.i("LikedPhotosViewModel", "Deleting $id from the DB")
                repository.deletePhotoFromDb(id)
            Log.i("LikedPhotosViewModel", "Unliking $id")
            repository.unlike_photo(id) // API call
        }
    }

    fun initLikedPhotos(username: String) {
        // Check if photos are in local cache and add them if not
        Log.d("LikedPhotosViewModel", "Adding photos to the DB if not already in it.")
        viewModelScope.launch {
            val apiPhotos = repository.getLikedPhotos(username)
            Log.i("LikedPhotosViewModel", "We have ${apiPhotos.size} photos in the API and ${repository.getCount()} in the DB")
            // Check if photos are in local cache
            for (photo in apiPhotos) {
                if (!repository.isInDb(photo.id)) {
                    Log.i("LikedPhotosViewModel", "Adding ${photo.id} to the DB")
                    repository.addPhotoToDb(photo.id)
                }
            }
        }
    }
    private fun daoToUnSplashPhoto(likedPhotos: List<LikedPhotos>) : List<UnSplashPhoto> {
        // Convert a LikedPhotos object to UnSplashPhoto object
        val photos = mutableListOf<UnSplashPhoto>()
        for (temp in likedPhotos) {
            photos.add(UnSplashPhoto(
                temp.photoId,
                "",
                "",
                "",
                0,
                0,
                "",
                "",
                temp.description,
                temp.description,
                Urls(temp.path, temp.path, temp.path, temp.path, temp.path),
                Links(temp.path, temp.path, temp.path, temp.path),
                listOf(),
                temp.likes,
                true,
                listOf(),
                UnSplashUser(
                ),
                0,
                0
            ))
        }
        return photos
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