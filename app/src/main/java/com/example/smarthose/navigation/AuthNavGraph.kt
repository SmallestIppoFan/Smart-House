package com.example.smarthose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.smarthose.screens.auth.Login
import com.example.smarthose.screens.auth.SignUp

fun NavGraphBuilder.authNavGraph(navController: NavController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ){
        composable(route = AuthScreen.Login.route){
            Login(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUp = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route){
            SignUp(onClick = {
                navController.navigate(AuthScreen.Login.route)
            })
        }
    }

}
sealed class AuthScreen(val route:String){
    object Login:AuthScreen(route = "LOGIN")
    object SignUp: AuthScreen(route = "SIGN_UP")
}