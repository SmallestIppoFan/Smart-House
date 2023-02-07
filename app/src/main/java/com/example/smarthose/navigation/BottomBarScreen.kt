package com.example.smarthose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route:String,
    val title: String?,
    val icon: ImageVector?
){
    object Home:BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object User:BottomBarScreen(
        route = "user",
        title = "User",
        icon = Icons.Default.Person
    )
    object Voice:BottomBarScreen(
        route = "camera",
        title = null,
        icon = null
    )
}
