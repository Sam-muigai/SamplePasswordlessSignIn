package com.samkt.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.ktx.Firebase
import com.samkt.sample.ui.theme.SampleTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val AUTHENTICATION = "authentication"

class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth = Firebase.auth
    private var email: MutableState<String>? = null

    private var cachedEmail: String = ""

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        if (intentHasEmailLink(intent)){
            Log.d(AUTHENTICATION,"I have data")
            getData(intent)
        }else{
            Log.d(AUTHENTICATION,"I dont have the data")
        }

        setContent {
            SampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        email = remember {
                            mutableStateOf("")
                        }
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email!!.value,
                            onValueChange = {
                                email!!.value = it
                            }
                        )
                        Button(
                            onClick = {
                                sendEmailLink(email!!.value)
                            }
                        ) {
                            Text(text = "SEND EMAIL LINK")
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        cachedEmail = email?.value ?: ""
    }

    private fun settings(email: String) = actionCodeSettings {

        url = "https://sample24.page.link/email-link-login"

        handleCodeInApp = true
        setIOSBundleId("com.example.ios")
        setAndroidPackageName(
            packageName,
            false, // installIfNotAvailable
            "12", // minimumVersion
        )
    }

    private fun sendEmailLink(email: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                auth.sendSignInLinkToEmail(email, settings(email)).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(AUTHENTICATION, "Email sent successfully")
                    } else {
                        Log.d(AUTHENTICATION, "Email not sent")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun intentHasEmailLink(intent: Intent?): Boolean {
        if (intent != null && intent.data != null) {
            val intentData = intent.data.toString()
            Log.d(AUTHENTICATION,intentData)
            return true
        }
        return false
    }

    private fun signInWithEmailLink(email: String, emailLink: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                auth.signInWithEmailLink(email, emailLink!!).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(AUTHENTICATION, "Sign In successful")
                    } else {
                        Log.d(AUTHENTICATION, "Sign Up Failed")
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                e.localizedMessage?.let { Log.d(AUTHENTICATION, it) }
            }
        }
    }

    private fun getData(intent: Intent) {
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener {
                if (it != null && it.link != null) {
                   val email = it.link?.getEmail() ?: "nO EMAIL"
                   Log.d(AUTHENTICATION,email)
                }
            }
    }

    private fun Uri.getEmail(): String? {
        return getQueryParameter("continueUrl")
            ?.toUri()
            ?.getQueryParameter("email")
    }
}

