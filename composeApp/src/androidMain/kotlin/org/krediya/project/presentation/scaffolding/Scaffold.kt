package org.krediya.project.presentation.scaffolding

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.krediya.project.presentation.navegation.Screen
import org.krediya.project.presentation.screens.screen1.ScreenOne
import org.krediya.project.presentation.screens.screen2.ScreenTwo
import org.krediya.project.presentation.screens.screen3.ScreenThree

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
    navController: NavHostController = rememberNavController()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val gesturesEnabled = rememberSaveable { mutableStateOf(false) }

    if (drawerState.isOpen) {
        LaunchedEffect(Unit) {
            gesturesEnabled.value = true
        }
    } else {
        LaunchedEffect(Unit) {
            gesturesEnabled.value = false
        }
    }

    ModalNavigationDrawer(
        gesturesEnabled = gesturesEnabled.value,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                drawerState = drawerState,
                navController = navController,
                coroutineScope = coroutineScope,
            )
        },
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                    )
                },
                bottomBar = {
                    BottomNavigationBar(navController)
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                },
                content = { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Screen1.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(
                            route = Screen.Screen1.route,
                        ) {
                            ScreenOne()
                        }

                        composable(
                            route = Screen.Screen2.route,
                        ) {
                            ScreenTwo()
                        }

                        composable(
                            route = Screen.Screen3.route,
                        ) {
                            ScreenThree()
                        }
                    }
                }
            )
        }
    )
}