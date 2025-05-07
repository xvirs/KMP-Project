package org.krediya.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import org.krediya.project.presentation.navegation.AppNavigation
import org.krediya.project.presentation.theme.MyTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTemplateTheme (
                dynamicColor = true,
            ) {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}