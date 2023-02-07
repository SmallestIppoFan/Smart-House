package com.example.smarthose.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smarthose.ControlBlock
import com.example.smarthose.ControlBlockItem
import com.example.smarthose.CustomChipGroup
import com.example.smarthose.Icons.TemperatureIcon
import com.example.smarthose.TemperatureBlock
import com.example.smarthose.ui.theme.BackgroundColor

@Composable
fun HomeScreen() {
    val data= listOf(
        ControlBlockItem.LightningBlock,
        ControlBlockItem.DoorBlock
    )
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
                LazyVerticalGrid(columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(15.dp)
                    ){
                    items(data){item->
                        ControlBlock(icon = item.icon, text =item.title )
                    }
                }
            }

        }
    }