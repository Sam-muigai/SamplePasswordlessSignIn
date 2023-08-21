package com.samkt.sample.data.model


val samplePost1 = Posts(
    profile_pic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzHQv_th9wq3ivQ1CVk7UZRxhbPq64oQrg5Q&usqp=CAU",
    user_name = "Martha Craig",
    user_label = "@craig_love",
    time_posted = "12h",
    post_info = "UXR/UX: You can only bring one item to a remote island to assis" +
            "t your research of native use of tools and usability. What do " +
            "you bring? #TellMeAboutYou",
    no_of_comments = 12,
    no_of_likes = 28,
    no_of_reposts = 0,
    is_thread = true,
    liked = true,
    shared_name = "Mwanzi"
)

val samplePost2 = Posts(
    profile_pic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfXpi1Nrns6Lg_qmU2V4jJ4kexQbqsgKyCxg&usqp=CAU",
    user_name = "Maximmilian",
    user_label = "@maxjacobson",
    time_posted = "3h",
    post_info = "Can your setup Even? 😁",
    no_of_comments = 32,
    no_of_likes = 18,
    no_of_reposts = 46,
    post_image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwytj4a1U1VgK4oteRrd96lnihdBdVYgwL1Q&usqp=CAU",
    retweeted = true,
    shared_name = "Mwania"
)

val samplePost3 = Posts(
    profile_pic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSxGmPt61A9-sKqT-RSMtF0YFZOMgGhCeJGw&usqp=CAU",
    user_name = "Tabitha Potter",
    user_label = "@mis_potter",
    time_posted = "14h",
    post_info = "Kobe’s passing is really sticking w/ me in a way I didn’t expect."+
            "He was an icon, the kind of person who wouldn’t die this way. My wife compared it to Princess Di’s accident."+
            "But the end can happen for anyone at any time, & I can’t help but think of anything else lately.",
    no_of_comments = 7,
    no_of_likes = 1,
    no_of_reposts = 11,
    is_thread = false,
    liked = false
)

val posts = listOf<Posts>(
    samplePost1,
    samplePost2,
    samplePost3
)

/*
* WwHEN WE BEHIND THE TWO Behind
* */