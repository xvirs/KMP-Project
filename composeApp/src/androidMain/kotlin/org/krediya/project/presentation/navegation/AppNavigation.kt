package org.krediya.project.presentation.navegation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.krediya.project.presentation.scaffolding.Scaffold

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "main"
    ){
        composable(
            route = "main",
        ) {
            Scaffold()
        }
    }
}