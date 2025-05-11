package org.krediya.project.presentation.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertIsNot
import kotlin.test.assertNotEquals

class UIStateTest {

    @Test
    fun `Loading es un UIState`() {
        val state = UIState.Loading

        assertIs<UIState<*>>(state)
        assertIs<UIState.Loading>(state)
    }

    @Test
    fun `Success contiene los datos correctos`() {
        // Given - Dado
        val testData = "Datos de prueba"

        // When - Cuando
        val state = UIState.Success(testData)

        // Then - Entonces
        assertIs<UIState<String>>(state)
        assertIs<UIState.Success<String>>(state)
        assertEquals(testData, state.data)
    }

    @Test
    fun `Error contiene el mensaje de error correcto`() {
        // Dado
        val errorMessage = "Algo salió mal"

        // Cuando
        val state = UIState.Error(errorMessage)

        // Entonces
        assertIs<UIState<*>>(state)
        assertIs<UIState.Error>(state)
        assertEquals(errorMessage, state.message)
    }

    @Test
    fun `Diferentes instancias de Success con los mismos datos son iguales`() {
        // Dado
        val data = "Datos de prueba"

        // Cuando
        val state1 = UIState.Success(data)
        val state2 = UIState.Success(data)

        // Entonces
        assertEquals(state1, state2)
        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `Diferentes instancias de Error con el mismo mensaje son iguales`() {
        // Dado
        val message = "Mensaje de error"

        // Cuando
        val state1 = UIState.Error(message)
        val state2 = UIState.Error(message)

        // Entonces
        assertEquals(state1, state2)
        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `Diferentes instancias de Loading son iguales`() {
        // Cuando
        val state1 = UIState.Loading
        val state2 = UIState.Loading

        // Entonces
        assertEquals(state1, state2)
        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `Diferentes tipos de estados no son iguales`() {
        // Dado
        val loading = UIState.Loading
        val success = UIState.Success("datos")
        val error = UIState.Error("mensaje")

        // Entonces
        assertIsNot<UIState.Loading>(success)
        assertIsNot<UIState.Loading>(error)
        assertIsNot<UIState.Success<*>>(loading)
        assertIsNot<UIState.Success<*>>(error)
        assertIsNot<UIState.Error>(loading)
        assertIsNot<UIState.Error>(success)
    }

    @Test
    fun `Estados Success con diferentes datos no son iguales`() {
        // Dado
        val state1 = UIState.Success("datos1")
        val state2 = UIState.Success("datos2")

        // Entonces
        assertNotEquals(state1, state2)
    }

    @Test
    fun `Estados Error con diferentes mensajes no son iguales`() {
        // Dado
        val state1 = UIState.Error("error1")
        val state2 = UIState.Error("error2")

        // Entonces
        assertNotEquals(state1, state2)
    }
}