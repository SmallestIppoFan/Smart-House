package com.example.smarthose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smarthose.screens.main.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController,
            route =Graph.ROOT,
            startDestination = Graph.AUTHENTICATION
        ){
        authNavGraph(navController=navController)
        composable(route = Graph.HOME){
            MainScreen()
        }
    }

}
object Graph{
    const val ROOT="root_graph"
    const val AUTHENTICATION= "auth_graph"
    const val HOME="bottom_graph"
    const val TOP="top_graph"
}