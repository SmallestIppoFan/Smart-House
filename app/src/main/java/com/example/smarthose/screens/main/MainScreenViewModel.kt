package com.example.smarthose.screens.main

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.*
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthose.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("StaticFieldLeak")
@HiltViewModel
class MainScreenViewModel @Inject constructor(private val database: FirebaseDatabase):ViewModel() {
    val temp1Value:MutableState<String> = mutableStateOf("")
    val temp2Value:MutableState<String> = mutableStateOf("")
    val humidity1Value:MutableState<String> = mutableStateOf("")
    val humidity2Value:MutableState<String> = mutableStateOf("")
    val gasValue:MutableState<String> = mutableStateOf("")
    fun changeState(number:Int,path:String){
        val key=database.getReference("$path")
        key.setValue(number)
    }

    init {
        getTemperature1()
        getTemperature2()
        getHumidity1()
        getHumidity2()
        getGas()
    }

    fun getTemperature1()=viewModelScope.launch{
        val myRef= database.getReference("AllData/encryptedTemperature1")
        val valueListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                temp1Value.value=snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("GASERROR", "GAS ERROR")
            }
        }
        myRef.addValueEventListener(valueListener)
    }

    fun getTemperature2()=viewModelScope.launch{
        val myRef= database.getReference("AllData/encryptedTemperature2")
        val valueListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                temp2Value.value=snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("GASERROR", "GAS ERROR")
            }
        }
        myRef.addValueEventListener(valueListener)
    }

    fun getHumidity1()=viewModelScope.launch{
        val myRef= database.getReference("AllData/encryptedHumidity1")
        val valueListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                humidity1Value.value=snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("GASERROR", "GAS ERROR")
            }
        }
        myRef.addValueEventListener(valueListener)
    }

    fun getHumidity2()=viewModelScope.launch{
        val myRef= database.getReference("AllData/encryptedHumidity2")
        val valueListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                humidity2Value.value=snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("GASERROR", "GAS ERROR")
            }
        }
        myRef.addValueEventListener(valueListener)
    }
    fun getGas()=viewModelScope.launch{
        val myRef= database.getReference("AllData/encryptedGasValue")
        val valueListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                gasValue.value=snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("GASERROR", "GAS ERROR")
            }
        }
        myRef.addValueEventListener(valueListener)
    }


}