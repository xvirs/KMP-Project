package org.krediya.project.shared.analytics

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AnalyticsManagerTest {

    private class TestAnalyticsService : AnalyticsService {
        val events = mutableListOf<Pair<String, Map<String, Any>>>()
        val userProperties = mutableMapOf<String, String>()

        override fun logEvent(name: String, params: Map<String, Any>) {
            events.add(name to params)
        }

        override fun setUserProperty(name: String, value: String) {
            userProperties[name] = value
        }
    }

    private class TestCrashlyticsService : CrashlyticsService {
        val exceptions = mutableListOf<Throwable>()
        val logs = mutableListOf<String>()
        // Cambiamos el nombre para evitar conflicto con setUserId
        private var _userIdValue: String? = null

        override fun logException(throwable: Throwable) {
            exceptions.add(throwable)
        }

        override fun log(message: String) {
            logs.add(message)
        }

        override fun setUserId(id: String) {
            _userIdValue = id
        }

        // Método para verificar el valor en las pruebas
        fun getUserId(): String? = _userIdValue
    }

    private lateinit var testAnalyticsService: TestAnalyticsService
    private lateinit var testCrashlyticsService: TestCrashlyticsService
    private lateinit var analyticsManager: AnalyticsManager

    @BeforeTest
    fun setup() {
        testAnalyticsService = TestAnalyticsService()
        testCrashlyticsService = TestCrashlyticsService()
        analyticsManager = AnalyticsManager(testAnalyticsService, testCrashlyticsService)
    }

    @Test
    fun `trackScreen deberia llamar a analytics logEvent con los parametros correctos`() {
        // Dado
        val screenName = "HomeScreen"

        // Cuando
        analyticsManager.trackScreen(screenName)

        // Entonces
        assertEquals(1, testAnalyticsService.events.size)
        val (eventName, params) = testAnalyticsService.events[0]
        assertEquals("screen_view", eventName)
        assertEquals(screenName, params["screen_name"])
    }

    @Test
    fun `trackEvent deberia llamar a analytics logEvent con los parametros correctos`() {
        // Dado
        val eventName = "button_click"
        val params = mapOf("button_id" to "login_button", "user_type" to "new")

        // Cuando
        analyticsManager.trackEvent(eventName, params)

        // Entonces
        assertEquals(1, testAnalyticsService.events.size)
        val (actualEventName, actualParams) = testAnalyticsService.events[0]
        assertEquals(eventName, actualEventName)
        assertEquals(params, actualParams)
    }

    @Test
    fun `trackEvent sin parametros deberia llamar a analytics logEvent con parametros vacíos`() {
        // Dado
        val eventName = "app_open"

        // Cuando
        analyticsManager.trackEvent(eventName)

        // Entonces
        assertEquals(1, testAnalyticsService.events.size)
        val (actualEventName, actualParams) = testAnalyticsService.events[0]
        assertEquals(eventName, actualEventName)
        assertEquals(emptyMap(), actualParams)
    }

    @Test
    fun `trackError debeia llamar a crashlytics logException`() {
        // Dado
        val exception = RuntimeException("Test exception")

        // Cuando
        analyticsManager.trackError(exception)

        // Entonces
        assertEquals(1, testCrashlyticsService.exceptions.size)
        assertEquals(exception, testCrashlyticsService.exceptions[0])
        assertEquals(0, testCrashlyticsService.logs.size)
    }

    @Test
    fun `trackError con mensaje deberia llamar a crashlytics logException y log`() {
        // Dado
        val exception = RuntimeException("Test exception")
        val message = "Error occurred during login"

        // Cuando
        analyticsManager.trackError(exception, message)

        // Entonces
        assertEquals(1, testCrashlyticsService.exceptions.size)
        assertEquals(exception, testCrashlyticsService.exceptions[0])
        assertEquals(1, testCrashlyticsService.logs.size)
        assertEquals(message, testCrashlyticsService.logs[0])
    }

    @Test
    fun `setUser deberia llamar a crashlytics setUserId`() {
        // Dado
        val userId = "user123"

        // Cuando
        analyticsManager.setUser(userId)

        // Entonces
        assertEquals(userId, testCrashlyticsService.getUserId())
    }
}