package com.example.smarthose.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthose.auth.data.AuthRepository
import com.example.smarthose.navigation.AuthScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.internal.ChannelFlow
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LoginViewModel (): ViewModel() {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    fun signIn(
        email: String = "SmartHouse@gmail.com",
        password: String,
        home : () -> Unit,
        toast: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            home()
                        } else {
                            toast()
                        }

                    }
            } catch (ex: Exception) {
                Log.d("FirebaseError", "signInWithEmailAndPassword: ${ex.message}")
            }
        }
    }
}