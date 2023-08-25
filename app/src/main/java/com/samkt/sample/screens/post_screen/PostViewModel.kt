package com.samkt.sample.screens.post_screen

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.sample.data.FireStoreRepository
import com.samkt.sample.data.FirebaseStorageRepository
import com.samkt.sample.data.model.Posts
import com.samkt.sample.util.UiEvents
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val STORAGE = "firebase_storage"

class PostViewModel : ViewModel() {
    private val fbStorage = FirebaseStorageRepository()
    private val fbFireStore = FireStoreRepository()

    private var _uiState = MutableStateFlow(PostScreenState())
    val uiState = _uiState.asStateFlow()

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun chooseImage(imageUri: Uri?) {
        _uiState.update {
            it.copy(
                imageUrl = imageUri?.toString()
            )
        }
    }

    fun onPostInfo(text:String){
        _uiState.update {
            it.copy(
                postInformation = text
            )
        }
    }
    fun createPost() {
        var firebaseUrl: Deferred<Uri?>? = null
        if (uiState.value.postInformation.isNotEmpty()){
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        loading = true
                    )
                }
                uiState.value.imageUrl?.let { uri ->
                    firebaseUrl = async { fbStorage.uploadImage(uri.toUri()) }
                }
                //Random number for likes,comment and repost
                val range = 1 until 34
                val image = if (firebaseUrl?.await() != null) firebaseUrl?.await().toString() else null
                val retweetedOrLiked = listOf(true,false,false,false)
                val names = listOf("Mwania","Elon Musk","Zuck")
                val profileInfo = sampleUsers.random()
                val post = Posts(
                    profile_pic = profileInfo.profileImage,
                    user_name = profileInfo.userName,
                    user_label = profileInfo.userLabel,
                    time_posted = "3h",
                    post_info = uiState.value.postInformation,
                    no_of_comments = range.random(),
                    no_of_likes = range.random(),
                    no_of_reposts = range.random(),
                    post_image = image,
                    retweeted = retweetedOrLiked.random(),
                    shared_name = names.random(),
                    liked = retweetedOrLiked.random()
                )
                val result = fbFireStore.addPost(post)
                if (result != null){
                    _uiState.update {
                        it.copy(
                            loading = false
                        )
                    }
                    _uiEvents.emit(UiEvents.ShowSnackBar("Tweet successfully posted"))
                    _uiEvents.emit(UiEvents.PopBackStack)
                }
            }
        }
    }
}

val sampleUsers = listOf(
    ProfileInfo(
        profileImage = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfXpi1Nrns6Lg_qmU2V4jJ4kexQbqsgKyCxg&usqp=CAU",
        userName = "Maximmilian",
        userLabel = "@maxjacobson"
    ),
    ProfileInfo(
        profileImage = "https://wallpapers.com/images/hd/cool-profile-picture-1ecoo30f26bkr14o.jpg",
        userName = "Samuel G",
        userLabel = "@SamMuigai"
    ),
    ProfileInfo(
        profileImage = "https://i.pinimg.com/originals/25/78/61/25786134576ce0344893b33a051160b1.jpg",
        userName = "Maina Karoki",
        userLabel = "@mainaKaroki"
    ),
)

data class ProfileInfo(
    val profileImage:String,
    val userName:String,
    val userLabel:String
)