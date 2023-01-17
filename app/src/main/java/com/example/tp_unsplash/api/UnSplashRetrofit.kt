package com.example.tp_unsplash.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UnSplashRetrofit {
    private const val BASE_URL = "https://api.unsplash.com"
    private const val TOKEN = "Client-ID liyybH-ytr_pvDVLhIgpjwHGIlPhJvoZ_oMWfopP3-A"
    fun getService(): UnSplashRetrofitService {
        val retrofitBuilder = Retrofit.Builder()

        // We need to add the authentication token to the header of each request
        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $TOKEN")
                .build()
            chain.proceed(newRequest)
        }).build()


        retrofitBuilder.client(client)

        val retrofit = retrofitBuilder.baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UnSplashRetrofitService::class.java)
    }
}