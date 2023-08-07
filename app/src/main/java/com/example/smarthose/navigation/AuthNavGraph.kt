package com.example.smarthose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.smarthose.screens.auth.Login

fun NavGraphBuilder.authNavGraph(navController: NavController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ){
        composable(route = AuthScreen.Login.route){
            Login(
                onClick = {
                    navController.navigate(Graph.HOME){
                        popUpTo(0)
                    }
                },
                navController=navController
            )
        }
    }
}
sealed class AuthScreen(val route:String){
    object Login:AuthScreen(route = "LOGIN")
    object SignUp: AuthScreen(route = "SIGN_UP")
}