package com.example.tp_unsplash.repository

import com.example.tp_unsplash.api.UnSplashRetrofitService
import com.example.tp_unsplash.dao.LikedPhotoDao
import com.example.tp_unsplash.models.LikedPhotos
import com.example.tp_unsplash.schemas.Links
import com.example.tp_unsplash.schemas.UnSplashPhoto
import com.example.tp_unsplash.schemas.UnSplashUser
import com.example.tp_unsplash.schemas.Urls

class UnSplashRepository(private val service: UnSplashRetrofitService,
                         private val dao: LikedPhotoDao) {
    suspend fun get_random_photo(count: Int = 10) : List<UnSplashPhoto> {
        return service.getData(count)
    }

    suspend fun like_photo(id: String) : UnSplashPhoto {
        if(dao.getLikedPhoto(id) == null) {
            val photo = service.likePhoto(id)
            val test = LikedPhotos(0,
            true,45,"test.png","edededed")
            dao.insert(test)
            return photo
        }
        return service.likePhoto(id)
    }

    suspend fun unlike_photo(id: String) : UnSplashPhoto {
        if(dao.getLikedPhoto(id) != null) {
            val photo = service.unlikePhoto(id)
            //dao.delete(photo)
            return photo
        }
        return service.unlikePhoto(id)
    }

    suspend fun get_liked_photos(username: String) : List<UnSplashPhoto> {
        val liked_photos = dao.getAllLikedPhotos()
        return if(liked_photos.isEmpty())
            service.getLikedPhotos(username)
        else
           return daoToUnSplashPhoto(liked_photos)
    }

    private fun daoToUnSplashPhoto(likedPhotos: List<LikedPhotos>) : List<UnSplashPhoto> {
        val photos = mutableListOf<UnSplashPhoto>()
        for (temp in likedPhotos) {
            photos.add(UnSplashPhoto(
                "",
                "",
                "",
                "",
                0,
                0,
                "",
                "",
                temp.description,
                temp.description,
                Urls(temp.path, temp.path, temp.path, temp.path, temp.path),
                Links(temp.path, temp.path, temp.path, temp.path),
                listOf(),
                temp.likes,
                true,
                listOf(),
                UnSplashUser(
                ),
                0,
                0
            ))
        }
        return photos
    }
}