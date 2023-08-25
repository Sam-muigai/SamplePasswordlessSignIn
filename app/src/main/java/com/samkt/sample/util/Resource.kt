package com.samkt.sample.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String?) : Result<T>(message = message)
}

suspend inline fun <reified T : Any> safeFirestoreRetrieval(collection: CollectionReference): Result<Flow<List<T>?>?> {
    return withContext(Dispatchers.IO) {
        try {
            val items = collection.snapshots().map {
                it.toObjects<T>()
            }
            Result.Success(items)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }
}
