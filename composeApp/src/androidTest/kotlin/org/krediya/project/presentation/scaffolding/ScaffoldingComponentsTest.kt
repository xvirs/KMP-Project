package org.krediya.project.presentation.scaffolding

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import org.junit.Rule
import org.junit.Test
import org.krediya.project.presentation.theme.MyTemplateTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
class ScaffoldingComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNavigationBarShowsCorrectItems() {
        // When
        composeTestRule.setContent {
            val context = LocalContext.current
            val navController = TestNavHostController(context)

            MyTemplateTheme {
                BottomNavigationBar(navController = navController)
            }
        }

        // Then
        composeTestRule.onNodeWithText("screen 1").assertExists()
        composeTestRule.onNodeWithText("screen 2").assertExists()
        composeTestRule.onNodeWithText("screen 3").assertExists()
    }

    @Test
    fun topBarShowsCorrectTitle() = runTest {
        // When
        composeTestRule.setContent {
            MyTemplateTheme {
                TopBar(
                    snackBarHostState = SnackbarHostState(),
                    coroutineScope = this,
                    drawerState = rememberDrawerState(DrawerValue.Closed)
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("My Template").assertExists()
    }
}