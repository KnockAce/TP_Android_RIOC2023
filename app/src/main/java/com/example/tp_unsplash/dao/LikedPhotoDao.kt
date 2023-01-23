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

    @Query("DELETE FROM liked_photos WHERE photo_id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM liked_photos")
    suspend fun deleteAll()

    @Query("SELECT * FROM liked_photos WHERE photo_id = :id")
    suspend fun getLikedPhoto(id: String) : LikedPhotos

    @Query("SELECT EXISTS(SELECT * FROM liked_photos WHERE photo_id = :id)")
    suspend fun isInDb(id: String) : Boolean

    @Query("SELECT COUNT(*) FROM liked_photos")
    suspend fun getCount() : Int
}