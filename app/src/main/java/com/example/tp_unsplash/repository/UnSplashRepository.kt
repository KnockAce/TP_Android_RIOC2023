package com.example.tp_unsplash.repository

import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.schemas.UnSplashPhoto

class UnSplashRepository(private val service: UnSplashRetrofitService) {
    suspend fun get_random_photo(count: Int = 10) : List<UnSplashPhoto> {
        return service.getData(count)
    }

    suspend fun like_photo(id: String) : UnSplashPhoto {
        return service.likePhoto(id)
    }

    suspend fun unlike_photo(id: String) : UnSplashPhoto {
        return service.unlikePhoto(id)
    }

    suspend fun get_liked_photos(username: String) : List<UnSplashPhoto> {
        return service.getLikedPhotos(username)
    }
}