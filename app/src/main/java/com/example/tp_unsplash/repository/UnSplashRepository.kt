package com.example.tp_unsplash.repository

import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.schemas.UnSplashPhoto

class UnSplashRepository(private val service: UnSplashRetrofitService) {
    suspend fun get_random_photo() : List<UnSplashPhoto> {
        return service.getData()
    }
}