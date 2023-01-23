package com.example.tp_unsplash

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tp_unsplash.dao.LikedPhotoDao
import com.example.tp_unsplash.models.LikedPhotos


@Database(
    entities = [LikedPhotos::class],
    version = 1)
abstract class UnSplashRoomDatabase : RoomDatabase() {
    abstract fun getLikedPhotoDao() : LikedPhotoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UnSplashRoomDatabase? = null

        fun getDatabase(context: Context): UnSplashRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnSplashRoomDatabase::class.java,
                    "UnSplashDatabase")
                //    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE liked_photos "
                    + "ADD COLUMN photo_id TEXT NOT NULL DEFAULT 'default_id'"
        )
    }
}