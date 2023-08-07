package com.example.smarthose.screens.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.smarthose.OtpTextField
import com.example.smarthose.navigation.Graph

@Composable
fun Login(
    onClick: () -> Unit,
    viewModel: LoginViewModel= androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {
    var loading by remember {
        mutableStateOf(false)
    }
    var otpValue by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        color = Color.White
    ) {
        if (loading==true){
            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Dialog(onDismissRequest = { }) {
                    Surface(elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .width(180.dp)
                            .height(90.dp)
                        ) {
                        DotsCollision()
                    }
                    }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Pin",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 200.dp)
            )

            Spacer(modifier = Modifier.heightIn(30.dp))
            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, otpInputFilled ->
                    otpValue = value
                }
            )
            if (otpValue.length == 6) {
                loading = true
                Log.d("ZAZA", "$otpValue")
                viewModel.signIn(password = otpValue,
                    home = {
                        navController.navigate(Graph.HOME) {
                            popUpTo(0)
                        }
                    }
                ) {
                    loading=false
                    otpValue=""
                    Toast.makeText(context, "Incorrect Login", Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.weight(0.9f))
            Text(text = "Contact technical support",
                color = Color.Blue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+xQE64N-K8AAzM2Ri"))
                    startActivity(context, intent, null)
                }
                )

        }
    }
}




@Composable
fun DotsCollision() {
    val dotSize = 24.dp // made it bigger for demo
    val maxOffset = 30f
    val delayUnit = 500 // it's better to use longer delay for this animation
    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(x = offset.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            )
    )
    val infiniteTransition = rememberInfiniteTransition()
    val offsetLeft by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 3
                0f at 0 with LinearEasing
                -maxOffset at delayUnit / 2 with LinearEasing
                0f at delayUnit
            }
        )
    )
    val offsetRight by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 3
                0f at delayUnit with LinearEasing
                maxOffset at delayUnit + delayUnit / 2 with LinearEasing
                0f at delayUnit * 2
            }
        )
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = maxOffset.dp)
    ) {
        val spaceSize = 2.dp
        Dot(offsetLeft)
        Spacer(Modifier.width(spaceSize))
        Dot(0f)
        Spacer(Modifier.width(spaceSize))
        Dot(offsetRight)
    }
}


