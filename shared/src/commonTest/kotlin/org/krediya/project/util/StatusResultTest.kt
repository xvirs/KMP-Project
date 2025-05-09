package org.krediya.project.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class StatusResultTest {

    @Test
    fun `StatusResult Success should hold data correctly`() {
        // Given
        val testData = "Test Data"

        // When
        val result = StatusResult.Success(testData)

        // Then
        assertTrue(result is StatusResult.Success)
        assertEquals(testData, result.data)
    }

    @Test
    fun `StatusResult Error should hold message correctly`() {
        // Given
        val errorMessage = "An error occurred"

        // When
        val result = StatusResult.Error(errorMessage)

        // Then
        assertTrue(result is StatusResult.Error)
        assertEquals(errorMessage, result.message)
    }

    @Test
    fun `StatusResult equals works correctly`() {
        // Given
        val data = "Test Data"
        val success1 = StatusResult.Success(data)
        val success2 = StatusResult.Success(data)
        val successWithDifferentData = StatusResult.Success("Different Data")
        val error: StatusResult<String> = StatusResult.Error("Error")

        // Then
        assertEquals(success1, success2)
        assertNotEquals(success1, successWithDifferentData)
        assertTrue(success1 != error)
    }
}