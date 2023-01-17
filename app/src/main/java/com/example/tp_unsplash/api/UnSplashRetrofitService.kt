package com.example.tp_unsplash.api

//import retrofit2.http.GET

interface UnSplashRetrofitService {
    //@GET("/photos/random")
    suspend fun getData(): List<String>
}