package com.example.tp_unsplash.api

import com.example.tp_unsplash.schemas.UnSplashPhoto
import retrofit2.http.GET

interface UnSplashRetrofitService {
    @GET("/photos/random?count=10")
    suspend fun getData(): List<UnSplashPhoto>
}