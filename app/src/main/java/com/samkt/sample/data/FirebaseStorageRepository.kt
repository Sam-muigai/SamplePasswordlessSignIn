package com.samkt.sample.data

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseStorageRepository {

    private val storageRef = Firebase.storage.reference

    suspend fun uploadImage(imageUri: Uri): Uri? = withContext(Dispatchers.IO) {
        val imageRef = storageRef.child("images")
        val uploadTask = imageRef.child("${imageUri.lastPathSegment}").putFile(imageUri)
        try {
            val task = uploadTask.await()
            task.storage.downloadUrl.await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
