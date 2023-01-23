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
        return service.likePhoto(id)
    }

    suspend fun addPhotoToDb(photoId:  String) {
        // TODO: Avoid to make a request to get the photo
        val photo = service.getPhoto(photoId)
        val description = photo.alt_description ?: "No description"
        val likedPhoto = LikedPhotos(0,
            true,photo.likes,photo.urls.full,description,photoId)
        dao.insert(likedPhoto)
    }

    suspend fun unlike_photo(id: String) : UnSplashPhoto {
        return service.unlikePhoto(id)
    }

    suspend fun deletePhotoFromDb(photoId: String) : Boolean {
        dao.delete(photoId)
        return true
    }

     suspend fun isInDb(photoId: String) : Boolean {
        return dao.isInDb(photoId)
    }

    suspend fun getDbLikedPhotos() : List<LikedPhotos> {
        return dao.getAllLikedPhotos()
    }

    suspend fun getLikedPhotos(username: String) : List<UnSplashPhoto> {
        return service.getLikedPhotos(username)
    }

    suspend fun getCount() : Int {

        return dao.getCount()
    }
}