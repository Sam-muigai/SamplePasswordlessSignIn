package com.samkt.sample.screens.post_screen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.sample.data.FireStoreRepository
import com.samkt.sample.data.FirebaseStorageRepository
import com.samkt.sample.data.model.Posts
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val STORAGE = "firebase_storage"

class PostViewModel : ViewModel() {
    val fbStorage = FirebaseStorageRepository()
    val fbFireStore = FireStoreRepository()


    var imageUrl: String? by mutableStateOf(null)
    var fbUrl: String? by mutableStateOf(null)

    var postInformation by mutableStateOf("")

    var successful = mutableStateOf("")

    fun chooseImage(imageUri: Uri?) {
        imageUrl = imageUri!!.toString()
    }

    fun onPostInfo(text:String){
        postInformation = text
    }
    fun createPost() {
        var firebaseUrl: Deferred<Uri?>? = null
        viewModelScope.launch {
            imageUrl?.let { uri ->
                firebaseUrl = async { fbStorage.uploadImage(uri.toUri()) }
            }
            val post = Posts(
                profile_pic = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfXpi1Nrns6Lg_qmU2V4jJ4kexQbqsgKyCxg&usqp=CAU",
                user_name = "Maximmilian",
                user_label = "@maxjacobson",
                time_posted = "3h",
                post_info = postInformation,
                no_of_comments = 32,
                no_of_likes = 18,
                no_of_reposts = 46,
                post_image = firebaseUrl?.await().toString(),
                retweeted = true,
                shared_name = "Mwania"
            )
            fbFireStore.addPost(post)
        }
    }
}