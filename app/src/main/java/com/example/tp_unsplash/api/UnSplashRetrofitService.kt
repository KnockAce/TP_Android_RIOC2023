package com.example.tp_unsplash.api

import com.example.tp_unsplash.schemas.UnSplashPhoto
import com.example.tp_unsplash.schemas.UserAuthBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnSplashRetrofitService {
    @POST("/oauth/token")
    suspend fun getToken(
        @Body body: UserAuthBody
    )


    @GET("/photos/random?count=10")
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
}