package com.samkt.sample.data.model


val samplePost1 = Posts(
    profilePic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzHQv_th9wq3ivQ1CVk7UZRxhbPq64oQrg5Q&usqp=CAU",
    userName = "Martha Craig",
    userLabel = "@craig_love",
    timePosted = "12h",
    postInfo = "UXR/UX: You can only bring one item to a remote island to assis" +
            "t your research of native use of tools and usability. What do " +
            "you bring? #TellMeAboutYou",
    noOfComments = 12,
    noOfLikes = 28,
    noOfReposts = 0,
    isThread = true,
    liked = true,
    sharedName = "Mwanzi"
)

val samplePost2 = Posts(
    profilePic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzHQv_th9wq3ivQ1CVk7UZRxhbPq64oQrg5Q&usqp=CAU",
    userName = "Maximmilian",
    userLabel = "@maxjacobson",
    timePosted = "3h",
    postInfo = "Y’all ready for this next post?",
    noOfComments = 32,
    noOfLikes = 18,
    noOfReposts = 46,
    postImage = "https://cdn.akamai.steamstatic.com/steam/apps/1144200/header.jpg?t=1688390724",
    retweeted = true,
    sharedName = "Mwania"
)

val samplePost3 = Posts(
    profilePic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzHQv_th9wq3ivQ1CVk7UZRxhbPq64oQrg5Q&usqp=CAU",
    userName = "Tabitha Potter",
    userLabel = "@mis_potter",
    timePosted = "14h",
    postInfo = "Kobe’s passing is really sticking w/ me in a way I didn’t expect.\n" +
            "\n" +
            "He was an icon, the kind of person who wouldn’t die this way. My wife compared it to Princess Di’s accident.\n" +
            "\n" +
            "But the end can happen for anyone at any time, & I can’t help but think of anything else lately.",
    noOfComments = 7,
    noOfLikes = 1,
    noOfReposts = 11,
    isThread = false,
    liked = false
)

val posts = listOf<Posts>(
    samplePost1,
    samplePost2
)