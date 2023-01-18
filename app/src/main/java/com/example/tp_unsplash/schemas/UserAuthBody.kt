package com.example.tp_unsplash.schemas

data class UserAuthBody(
    val client_id: String,
    val client_secret: String,
    val redirect_uri: String,
    val code: String,
    val grant_type: String
)
