package com.example.smarthose.screens.home

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthose.ControlBlock
import com.example.smarthose.ControlBlockItem
import com.example.smarthose.CustomChipGroup
import com.example.smarthose.Icons.TemperatureIcon
import com.example.smarthose.TemperatureBlock
import com.example.smarthose.navigation.BottomBarScreen
import com.example.smarthose.navigation.HomeNavGraph
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