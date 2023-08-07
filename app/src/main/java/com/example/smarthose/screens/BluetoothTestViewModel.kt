package com.example.smarthose.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.smarthose.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BluetoothTestViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) :ViewModel() {
    val conntected:MutableState<Boolean> = mutableStateOf(false)
    fun connectToDevice(device: BluetoothDevice) {
        val thread = ConnectThread(device)
        thread.start()
    }

    @SuppressLint("MissingPermission")
    private inner class ConnectThread(private val device: BluetoothDevice) : Thread() {

        private val socket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        }

        override fun run() {
            try {
                socket?.connect()
                conntected.value=true
            } catch (e: IOException) {
                Log.d("ConnectThread", "Не удалось подключиться к устройству", e)
                conntected.value=false
            }
        }
    }
}