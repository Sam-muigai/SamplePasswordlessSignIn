package com.samkt.sample.data

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.samkt.sample.data.model.Posts
import com.samkt.sample.util.Result
import com.samkt.sample.util.safeFirestoreRetrieval
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val FIRESTORE = "fireStore"
typealias TweetPosts = Flow<List<Posts>?>?

class FireStoreRepository {

    private val fireStore = Firebase.firestore

    private val collection = fireStore.collection("posts")

    suspend fun addPost(posts: Posts) =
        withContext(Dispatchers.IO) {
            try {
                collection.add(posts).await()
            } catch (e: Exception) {
                e.printStackTrace()
                e.localizedMessage?.let { Log.d(FIRESTORE, it) }
                null
            }
        }

    suspend fun getPosts(): Flow<Result<TweetPosts>> = flowOf(safeFirestoreRetrieval(collection))
}


