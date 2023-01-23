package com.example.tp_unsplash.dao

import androidx.room.*
import com.example.tp_unsplash.models.LikedPhotos
import com.example.tp_unsplash.schemas.UnSplashPhoto

@Dao
interface LikedPhotoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(LikedPhotos: LikedPhotos)

    @Query("SELECT * FROM liked_photos")
    suspend fun getAllLikedPhotos() : List<LikedPhotos>

    @Delete
    suspend fun delete(LikedPhotos: LikedPhotos)

    @Query("DELETE FROM liked_photos")
    suspend fun deleteAll()

    @Query("SELECT * FROM liked_photos WHERE id = :id")
    suspend fun getLikedPhoto(id: String) : LikedPhotos
}