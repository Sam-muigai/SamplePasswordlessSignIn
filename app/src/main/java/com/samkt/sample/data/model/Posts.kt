package com.samkt.sample.data.model

/*
* Refactor to deserialize firestore data later
* */

data class Posts(
    val profile_pic: String? = null,
    val user_name: String,
    val user_label: String,
    val time_posted: String,
    val post_info: String,
    val is_thread: Boolean = false,
    val no_of_comments: Int,
    val no_of_likes: Int,
    val no_of_reposts: Int,
    val post_image: String? = null,
    val retweeted: Boolean = false,
    val liked: Boolean = false,
    val shared_name: String = "",
) {
    constructor() : this(
        "", "", "", "", "",
        false, 0, 0, 0, "",
        false, false,
    )
}


















