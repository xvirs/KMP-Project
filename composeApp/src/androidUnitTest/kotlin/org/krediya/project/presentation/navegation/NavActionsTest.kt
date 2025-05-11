// Ruta: composeApp/src/test/kotlin/org/krediya/project/presentation/navegation/NavActionsTest.kt

package org.krediya.project.presentation.navegation

import androidx.navigation.NavController
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class NavActionsTest {

    @Test
    fun `La navegacion a Screen1 usa la ruta correcta`() {
        // Given
        val mockNavController = mockk<NavController>(relaxed = true)

        // When
        mockNavController.navigate(Screen.Screen1.route)

        // Then
        verify { mockNavController.navigate("screen1") }
    }

    @Test
    fun `La navegacion a Screen2 usa la ruta correcta`() {
        // Given
        val mockNavController = mockk<NavController>(relaxed = true)

        // When
        mockNavController.navigate(Screen.Screen2.route)

        // Then
        verify { mockNavController.navigate("screen2") }
    }

    @Test
    fun `La navegacion a Screen3 usa la ruta correcta`() {
        // Given
        val mockNavController = mockk<NavController>(relaxed = true)

        // When
        mockNavController.navigate(Screen.Screen3.route)

        // Then
        verify { mockNavController.navigate("screen3") }
    }
}