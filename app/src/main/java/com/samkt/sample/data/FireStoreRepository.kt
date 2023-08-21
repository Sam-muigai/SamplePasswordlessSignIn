package com.samkt.sample.data

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.samkt.sample.data.model.Posts
import com.samkt.sample.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


const val FIRESTORE = "fireStore"

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


    fun getPosts(): Flow<List<Posts?>>? {
        return try {
            collection.snapshots().map {
                it.toObjects()
            }
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

}