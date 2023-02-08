package com.example.smarthose.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthose.BottomNav
import com.example.smarthose.Icons.MicrophoneIcon
import com.example.smarthose.TopBar
import com.example.smarthose.navigation.BottomBarScreen
import com.example.smarthose.navigation.HomeNavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
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
                    BottomBarScreen.Voice.route?.let {
                        navController.navigate(it) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    BottomBarScreen.Voice.route?.let { navController.navigate(it) }
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