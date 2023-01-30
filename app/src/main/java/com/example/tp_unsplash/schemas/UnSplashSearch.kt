package com.example.tp_unsplash.schemas

data class UnSplashSearch(
    val results: List<UnSplashPhoto>,
    val total: Int,
    val total_pages: Int
)
