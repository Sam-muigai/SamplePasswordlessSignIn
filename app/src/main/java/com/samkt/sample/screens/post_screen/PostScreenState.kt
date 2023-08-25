package com.samkt.sample.screens.post_screen

data class PostScreenState(
    val loading: Boolean = false,
    val imageUrl: String? = null,
    val postInformation: String = "",
)
