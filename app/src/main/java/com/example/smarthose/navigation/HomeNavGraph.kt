package com.example.smarthose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smarthose.screens.home.HomeScreen
import com.example.smarthose.screens.user.UserScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController =navController ,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
        ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.User.route) {
            UserScreen()
        }
    }
}