package com.example.tp_unsplash.schemas

data class UnSplashUser(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String,
    val twitter_username: String,
    val portfolio_url: String,
    val bio: String,
    val location: String,
    val links: Links,
    val instagram_username: String,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val accepted_tos: Boolean
)
