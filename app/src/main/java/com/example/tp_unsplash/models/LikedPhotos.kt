package com.example.tp_unsplash.models

import androidx.room.*
import java.util.Date
import java.time.OffsetDateTime

@Entity(tableName = "liked_photos")
data class LikedPhotos(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "liked_by_user", defaultValue = "true")
    val likedByUser: Boolean,

    @ColumnInfo(name = "likes", defaultValue = "0")
    val likes: Int,

    @ColumnInfo(name = "path", defaultValue = "default_img") // TODO: change default value
    val path: String,

    @ColumnInfo(name = "description", defaultValue = "No description")
    val description: String,

    @ColumnInfo(name = "photo_id", defaultValue = "default_id")
    val photoId: String,

//    @ColumnInfo(name = "updated_at")
//    @TypeConverters(DateTimeConverter::class)
//    val timestamp: Date? = null
)
