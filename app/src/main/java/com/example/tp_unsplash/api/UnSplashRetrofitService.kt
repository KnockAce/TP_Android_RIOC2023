package com.example.tp_unsplash.api

import com.example.tp_unsplash.schemas.UnSplashPhoto
import com.example.tp_unsplash.schemas.UnSplashSearch
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnSplashRetrofitService {

    @GET("/photos/random")
    suspend fun getData(
        @Query("count") count: Int
    ): List<UnSplashPhoto>

    @POST("/photos/{id}/like")
    suspend fun likePhoto(
        @Path("id") id: String
    ): UnSplashPhoto

    @DELETE("/photos/{id}/like")
    suspend fun unlikePhoto(
        @Path("id") id: String
    ): UnSplashPhoto

    @GET("/users/{username}/likes")
    suspend fun getLikedPhotos(
        @Path("username") username: String
    ): List<UnSplashPhoto>

    @GET("/photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ): UnSplashPhoto

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): UnSplashSearch
}