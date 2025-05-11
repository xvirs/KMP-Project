package org.krediya.project.shared.analytics

/**
 * Implementación de AnalyticsService específica para pruebas
 * que no depende de Firebase ni de Android.
 */
class TestFirebaseAnalyticsService : AnalyticsService {

    val events = mutableListOf<Event>()
    val userProperties = mutableMapOf<String, String>()

    data class Event(val name: String, val params: Map<String, Any>)

    override fun logEvent(name: String, params: Map<String, Any>) {
        events.add(Event(name, params))
    }

    override fun setUserProperty(name: String, value: String) {
        userProperties[name] = value
    }
}