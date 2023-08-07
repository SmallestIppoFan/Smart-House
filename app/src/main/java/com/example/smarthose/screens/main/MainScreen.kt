package com.example.smarthose.screens.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smarthose.BottomNav
import com.example.smarthose.Icons.MicrophoneIcon
import com.example.smarthose.R
import com.example.smarthose.TopBar
import com.example.smarthose.navigation.HomeNavGraph
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController = rememberNavController(),viewModel: MainScreenViewModel= hiltViewModel()) {
    var outputTxt by remember { mutableStateOf("") }
    val actionKeywords = listOf("turn on", "turn off","open", "close")
    val roomKeywords = listOf("kitchen", "bedroom","living room","guest room")
    val context = LocalContext.current
    val speechRecognizerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!resultData.isNullOrEmpty()) {
                outputTxt = resultData[0].toString().toLowerCase()
            }
        }
    }
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomAppBar(
            modifier = Modifier
                .height(65.dp)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
            Color.White,
            cutoutShape = CircleShape,
            elevation = 22.dp
        ) {
            BottomNav(navController = navController)
        }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    getSpeechInput(context, speechRecognizerLauncher)
                          },
                contentColor = Color.White
            ) {
                Image(
                    painter = painterResource(id = MicrophoneIcon),
                    contentDescription = "Icon of micro",
                    modifier = Modifier.size(40.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    ) {
        HomeNavGraph(navController = navController)
    }
}
private fun getSpeechInput(context: Context, launcher: ActivityResultLauncher<Intent>) {
    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
        Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
    } else {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")
        }
        // launch the intent
        launcher.launch(intent)
    }
}


fun actionForKeyword(keyword:String,
                     viewModel: MainScreenViewModel,
                     text: String,
                     audioPlayer: AudioPlayer
                     ) {
    when (keyword) {
        "turn on" -> {
            if (text.contains("kitchen", ignoreCase = true)) {
                viewModel.changeState(1,"Kitchen_LED_STATE")
            }
            else if (text.contains("bedroom", ignoreCase = true)) {
                viewModel.changeState(1, "Bedroom_LED_STATE")
            }
            else if (text.contains("living room", ignoreCase = true)) {
                viewModel.changeState(1, "Living_room_LED_STATE")
            }
        }
        "turn off" -> {
            if (text.contains("kitchen", ignoreCase = true)) {
                viewModel.changeState(0,"Kitchen_LED_STATE")
            }
            else if (text.contains("bedroom", ignoreCase = true)) {
                viewModel.changeState(0, "Bedroom_LED_STATE")
            }
            else if (text.contains("living room", ignoreCase = true)) {
                viewModel.changeState(0, "Bedroom_LED_STATE")
            }
        }
        "close" -> {
            if (text.contains("kitchen", ignoreCase = true)) {
                viewModel.changeState(0,"Kitchen_SERVO_STATE")
            }
            else if (text.contains("living room", ignoreCase = true)) {
                viewModel.changeState(0, "Living_SERVO_STATE")
            }

        }

        "open" -> {
            if (text.contains("kitchen", ignoreCase = true)) {
                viewModel.changeState(1,"Kitchen_SERVO_STATE")
            }
            else if (text.contains("living room", ignoreCase = true)) {
                viewModel.changeState(1, "Living_SERVO_STATE")
            }
        }
        else ->{
            Log.d("AUDIDID","ZAZA")
        }

    }
}

fun searchKeywordsAndAct(text: String,
                         actionKeywords: List<String>,
                         roomKeywords: List<String>,
                         viewModel: MainScreenViewModel,
                         context: Context,
                         audioPlayer: AudioPlayer
                         ) {
    for (keyword in actionKeywords) {
        if (text.contains(keyword, ignoreCase = true)) {
            actionForKeyword(keyword,
                viewModel,
                text,
                audioPlayer
                )
        }
    }
}


class AudioPlayer(context: Context, @RawRes resId: Int) {
    private var mediaPlayer: MediaPlayer? = null

    init {
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            setOnCompletionListener {
                it.release()
                mediaPlayer = null
            }
        }
    }

    fun play() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
