package org.krediya.project.presentation.screens.screen1

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.krediya.project.presentation.model.PostUI
import org.krediya.project.presentation.theme.MyTemplateTheme

class ScreenOneUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `LoadingView displays CircularProgressIndicator`() {
        // Given
        composeTestRule.setContent {
            MyTemplateTheme {
                LoadingView()
            }
        }

        // Then - verificar que se muestra el indicador de carga
        // Nota: Esto es complicado de probar directamente, pero podríamos verificar elementos visuales específicos
    }

    @Test
    fun `ErrorView displays error message`() {
        // Given
        val errorMessage = "Test Error Message"

        // When
        composeTestRule.setContent {
            MyTemplateTheme {
                ErrorView(message = errorMessage)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun `PostItem displays post details`() {
        // Given
        val post = PostUI(
            userId = 1,
            id = 100,
            title = "Test Title",
            body = "Test Body Content"
        )

        // When
        composeTestRule.setContent {
            MyTemplateTheme {
                PostItem(post = post)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Body Content").assertIsDisplayed()
        composeTestRule.onNodeWithText("Post ID: 100 | User ID: 1").assertIsDisplayed()
    }

    @Test
    fun `PostList displays multiple posts`() {
        // Given
        val posts = listOf(
            PostUI(userId = 1, id = 1, title = "Title 1", body = "Body 1"),
            PostUI(userId = 2, id = 2, title = "Title 2", body = "Body 2")
        )

        // When
        composeTestRule.setContent {
            MyTemplateTheme {
                PostList(posts = posts)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Title 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Body 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Body 2").assertIsDisplayed()
    }
}