package com.example.tp_unsplash.schemas

import java.io.Serializable

data class Links(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
) : Serializable
