package org.krediya.project.shared.analytics

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FirebaseAnalyticsServiceTest {

    private lateinit var testService: TestFirebaseAnalyticsService

    @Before
    fun setUp() {
        testService = TestFirebaseAnalyticsService()
    }

    @Test
    fun `logEvent captura el nombre del evento y los parametros correctamente`() {
        // Dado
        val name = "test_event"
        val params = mapOf(
            "string_param" to "value",
            "int_param" to 123,
            "boolean_param" to true
        )

        // Cuando
        testService.logEvent(name, params)

        // Entonces
        assertEquals(1, testService.events.size)
        val event = testService.events.first()
        assertEquals(name, event.name)
        assertEquals(params, event.params)
    }

    @Test
    fun `setUserProperty captura el nombre de la propiedad y el valor correctamente`() {
        // Dado
        val propertyName = "user_level"
        val propertyValue = "premium"

        // Cuando
        testService.setUserProperty(propertyName, propertyValue)

        // Entonces
        assertTrue(propertyName in testService.userProperties)
        assertEquals(propertyValue, testService.userProperties[propertyName])
    }
}