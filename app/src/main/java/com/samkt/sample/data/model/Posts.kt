package com.samkt.sample.data.model

data class Posts(
    val profilePic:String? = null,
    val userName:String,
    val userLabel:String,
    val timePosted:String,
    val postInfo:String,
    val isThread:Boolean = false,
    val noOfComments:Int,
    val noOfLikes:Int,
    val noOfReposts:Int,
    val postImage:String? = null,
    val retweeted:Boolean = false,
    val liked:Boolean = false,
    val sharedName:String = ""
)
