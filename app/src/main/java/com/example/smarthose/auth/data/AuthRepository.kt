package com.example.smarthose.auth.data

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginPin(email:String,pin:String):Flow<Resource<AuthResult>>
}