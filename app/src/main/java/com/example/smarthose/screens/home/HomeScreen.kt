package com.example.smarthose.screens.home

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.smarthose.*
import com.example.smarthose.Icons.TemperatureIcon
import com.example.smarthose.R
import com.example.smarthose.screens.main.MainScreenViewModel
import com.example.smarthose.ui.theme.BackgroundColor
import com.example.smarthose.ui.theme.ControlBlockItem
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(viewModel: MainScreenViewModel= hiltViewModel()) {
    val context= LocalContext.current
    val doorSwitch = remember {
        mutableStateOf(false)
    }
    val lightSwitch = remember {
        mutableStateOf(false)
    }
    val data= listOf(
        ControlBlockItem.LightningBlock,
        ControlBlockItem.DoorBlock
    )
    val selectedRoom = remember{
        mutableStateOf("")
    }

    var temp1 = remember {
        mutableStateOf("")
    }
    var temp2 = remember {
        mutableStateOf("")
    }
    var hum1 = remember {
        mutableStateOf("")
    }
    var hum2 = remember {
        mutableStateOf("")
    }
    var roomTemp= remember {
        mutableStateOf("")
    }
    var roomHum= remember {
        mutableStateOf("")
    }

    var kitchenGas= remember {
        mutableStateOf("")
    }

    var roomGas= remember {
        mutableStateOf("")
    }

    val key = byteArrayOf(
        0x00, 0x01, 0x02, 0x03,
        0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B,
        0x0C, 0x0D, 0x0E, 0x0F
    )

    if (viewModel.humidity1Value.value != ""
        && viewModel.humidity2Value.value != ""
        && viewModel.temp1Value.value != ""
        && viewModel.temp2Value.value != ""

    ) {
        val temp1HexData = removeSpaces(viewModel.temp1Value.value)
        val temp2HexData = removeSpaces( viewModel.temp2Value.value)
        val hum1HexData = removeSpaces(viewModel.humidity1Value.value)
        val hum2HexData = removeSpaces(viewModel.humidity2Value.value)
        val gasHexData= removeSpaces(viewModel.gasValue.value)

        val encryptedDataTemp1 = hexToByteArray(temp1HexData)
        val encryptedDataTemp2 = hexToByteArray(temp2HexData)
        val encryptedDataHum1 = hexToByteArray(hum1HexData)
        val encryptedDataHum2 = hexToByteArray(hum2HexData)

        val encryptedGasValue = hexToByteArray(gasHexData)



        val decryptedTemp1 = decryptAES(encryptedDataTemp1, key)
        val decryptedTemp2 = decryptAES(encryptedDataTemp2, key)
        val decryptedHum1 = decryptAES(encryptedDataHum1, key)
        val decryptedHum2= decryptAES(encryptedDataHum2, key)
        val decryptedGas= decryptAES(encryptedGasValue, key)

        temp1.value=decryptedTemp1.toString()
        temp2.value=decryptedTemp2.toString()
        hum1.value=decryptedHum1.toString()
        hum2.value=decryptedHum2.toString()
        kitchenGas.value=decryptedGas.toString()
        Log.d("DANNie","${temp1.value} ${temp2.value} ${hum1.value} ${hum2.value} ${kitchenGas.value}")
    }
    if (selectedRoom.value=="Kitchen"){
        roomTemp.value=temp1.value
        roomGas.value = kitchenGas.value
        roomHum.value=hum1.value
        sendNotification(context = context)

    }
    else if (selectedRoom.value=="Guest room"){
        roomTemp.value=temp2.value
        roomHum.value=hum2.value
    }
    else{
        roomTemp.value="25"
        roomHum.value="20"
        roomGas.value="19"
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomChipGroup(selectedRoom)
            if (selectedRoom.value == "") {

            } else {
                TemperatureBlock(
                    temperature = "${roomTemp.value} °C",
                    roomName = "${selectedRoom.value} ",
                    backgroundColor = BackgroundColor,
                    type = "Tempertaure",
                    icon = painterResource(
                        id = TemperatureIcon
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TemperatureBlock(
                    temperature = "${roomHum.value} RH / ${roomGas.value} m³ ", roomName = "${selectedRoom.value} ", backgroundColor = BackgroundColor,
                    icon = painterResource(
                        id = R.drawable.humidity,
                    ),
                    type = "Humidity"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, top = 15.dp, end = 10.dp)
                ) {
                    ControlBlock(
                        icon = data[0].icon,
                        text = data[0].title,
                        selected = lightSwitch
                    ) {
                        if (lightSwitch.value) {
                            viewModel.changeState(1, "${selectedRoom.value}_LED_STATE")
                        } else {
                            viewModel.changeState(0, "${selectedRoom.value}_LED_STATE")
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    ControlBlock(icon = data[1].icon, text = data[1].title, selected = doorSwitch) {
                        if (doorSwitch.value) {
                            viewModel.changeState(1, "SERVO_STATUS")
                        } else {
                            viewModel.changeState(0, "SERVO_STATUS")
                        }
                        Log.d("VAVAV", "${doorSwitch.value}")

                    }
                }
            }
        }
    }
}


fun hexToByteArray(hex: String): ByteArray {
    val len = hex.length
    val data = ByteArray(len / 2)
    for (i in 0 until len step 2) {
        data[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
    }
    return data
}

fun removeSpaces(input: String): String {
    return input.replace(" ", "")
}
@SuppressLint("NotificationPermission")
private fun sendNotification(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Проверяем версию Android, чтобы создать канал уведомлений, требующийся для API 26 и выше.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(notificationManager)
    }

    // Создаем и отправляем уведомление.
    val notification = createNotification(context)
    notificationManager.notify(12, notification)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(notificationManager: NotificationManager) {
    val channelId = "my_channel_id"
    val channelName = "My Channel"
    val channelDescription = "Описание канала"

    val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
    channel.description = channelDescription

    notificationManager.createNotificationChannel(channel)
}

private fun createNotification(context: Context): Notification {
    val channelId = "my_channel_id"
    val contentTitle = "Заголовок уведомления"
    val contentText = "Текст уведомления"
    val smallIcon = R.drawable.flame
    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(contentTitle)
        .setContentText(contentText)
    // Другие настройки уведомления
    notificationBuilder.setSmallIcon(smallIcon)
    return notificationBuilder.build()
}