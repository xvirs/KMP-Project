package org.krediya.project.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertIs

class StatusResultTest {

    @Test
    fun `al crear Success deberia contener los datos correctos`() {
        // Dado
        val expectedData = "Test data"

        // Cuando
        val result = StatusResult.Success(expectedData)

        // Entonces
        assertIs<StatusResult.Success<String>>(result)
        assertEquals(expectedData, result.data)
    }

    @Test
    fun `al crear Error deberia contener el mensaje de error correcto`() {
        // Dado
        val expectedErrorMessage = "Error occurred"

        // Cuando
        val result = StatusResult.Error(expectedErrorMessage)

        // Entonces
        assertIs<StatusResult.Error>(result)
        assertEquals(expectedErrorMessage, result.message)
    }

    @Test
    fun `al usar StatusResult con diferentes parametros de tipo debería funcionar correctamente`() {
        // Dado
        val intValue = 42
        val stringValue = "Hello"

        // Cuando
        val intResult = StatusResult.Success(intValue)
        val stringResult = StatusResult.Success(stringValue)

        // Entonces
        assertIs<StatusResult.Success<Int>>(intResult)
        assertEquals(intValue, intResult.data)

        assertIs<StatusResult.Success<String>>(stringResult)
        assertEquals(stringValue, stringResult.data)
    }

    @Test
    fun `al usar pattern matching deberia ejecutar la rama correcta`() {
        // Dado
        val successResult: StatusResult<Int> = StatusResult.Success(42)
        val errorResult: StatusResult<Int> = StatusResult.Error("Error")

        // Cuando
        val successMessage = when (successResult) {
            is StatusResult.Success -> "Success: ${successResult.data}"
            is StatusResult.Error -> "Error: ${successResult.message}"
        }

        val errorMessage = when (errorResult) {
            is StatusResult.Success -> "Success: ${errorResult.data}"
            is StatusResult.Error -> "Error: ${errorResult.message}"
        }

        // Entonces
        assertEquals("Success: 42", successMessage)
        assertEquals("Error: Error", errorMessage)
    }

    @Test
    fun `verificar comportamiento de borrado de tipos con genéricos`() {
        // Dado
        val stringResult = StatusResult.Success("test")
        val intResult = StatusResult.Success(123)

        // Cuando y Entonces
        assertTrue(stringResult is StatusResult.Success<*>)
        assertTrue(intResult is StatusResult.Success<*>)
    }
}