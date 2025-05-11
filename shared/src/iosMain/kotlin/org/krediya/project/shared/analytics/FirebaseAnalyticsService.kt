package org.krediya.project.shared.analytics

actual open class FirebaseAnalyticsService : AnalyticsService {
    actual override fun logEvent(name: String, params: Map<String, Any>) {
        // Implementación para iOS
        println("Analytics event: $name, params: $params")
    }

    actual override fun setUserProperty(name: String, value: String) {
        // Implementación para iOS
        println("Set user property: $name = $value")
    }
}