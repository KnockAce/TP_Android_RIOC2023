package com.example.tp_unsplash.api

import com.example.tp_unsplash.schemas.UnSplashPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashRetrofitService {
    @GET("/photos/random?count=10")
    suspend fun getData(
        @Query("count") count: Int
    ): List<UnSplashPhoto>
}