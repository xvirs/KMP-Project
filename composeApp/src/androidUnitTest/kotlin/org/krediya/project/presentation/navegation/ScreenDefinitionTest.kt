package org.krediya.project.presentation.navegation

import org.junit.Test
import org.krediya.project.R
import kotlin.test.assertEquals

class ScreenDefinitionTest {

    @Test
    fun `Screen1 tiene la ruta y el recurso correctos`() {
        val screen = Screen.Screen1
        assertEquals("screen1", screen.route)
        assertEquals(R.string.screen1, screen.resourceId)
    }

    @Test
    fun `Screen2 tiene la ruta y el recurso correctos`() {
        val screen = Screen.Screen2
        assertEquals("screen2", screen.route)
        assertEquals(R.string.screen2, screen.resourceId)
    }

    @Test
    fun `Screen3 tiene la ruta y el recurso correctos`() {
        val screen = Screen.Screen3
        assertEquals("screen3", screen.route)
        assertEquals(R.string.screen3, screen.resourceId)
    }
}