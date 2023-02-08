package com.example.smarthose.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smarthose.ControlBlock
import com.example.smarthose.CustomChipGroup
import com.example.smarthose.Icons.TemperatureIcon
import com.example.smarthose.TemperatureBlock
import com.example.smarthose.ui.theme.BackgroundColor
import com.example.smarthose.ui.theme.ControlBlockItem

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen() {
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
    val outputText by mutableStateOf("outout text")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
            Column(
                modifier = Modifier.fillMaxSize()

            ) {
                CustomChipGroup()
                TemperatureBlock(temperature = "23", roomName ="Kitchen", backgroundColor = BackgroundColor, icon = painterResource(
                    id = TemperatureIcon
                ))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp)){
                    ControlBlock(icon = data[0].icon, text = data[0].title, selected = doorSwitch)
                    Spacer(modifier = Modifier.width(25.dp))
                    ControlBlock(icon = data[1].icon, text = data[1].title, selected = lightSwitch)

                }
            }

        }
    }