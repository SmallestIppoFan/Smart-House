package com.example.smarthose.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.smarthose.navigation.AuthScreen

@Composable
fun Login(
    onClick: () -> Unit,
    onSignUp :() ->Unit,

) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(
                text = "Login Page",
                fontSize = 25.sp
            )
            Text(
                text = "Login",
                modifier = Modifier.clickable {
                    onClick()
                }
            )
            Text(
                text = "Sign Up",
                modifier = Modifier.clickable {
                    onSignUp()
                }

            )

        }

    }

}