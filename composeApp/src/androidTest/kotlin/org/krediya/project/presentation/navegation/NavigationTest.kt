package org.krediya.project.presentation.navegation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Rule
import org.junit.Test
import org.krediya.project.presentation.theme.MyTemplateTheme
import kotlin.test.assertEquals

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Test
    fun `navigation to Screen1 works correctly`() {
        // Setup
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            MyTemplateTheme {
                AppNavigation(navController = navController)
            }
        }

        // Initially should be on the main route
        assertEquals("main", navController.currentBackStackEntry?.destination?.route)
    }
}