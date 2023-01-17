package com.example.tp_unsplash.schemas

data class UnSplashPhoto(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val promoted_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val blur_hash: String,
    val description: String,
    val alt_description: String,
    val urls: Urls,
    val links: Links,
    val categories: List<String>,
    val likes: Int,
    val liked_by_user: Boolean,
    val current_user_collections: List<String>,
    val user: UnSplashUser,
    val views: Int,
    val downloads: Int
)
