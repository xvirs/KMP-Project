package org.krediya.project.shared.analytics

actual class FirebaseAnalyticsService : AnalyticsService {
    override fun logEvent(name: String, params: Map<String, Any>) {
        // Implementación para iOS
        println("Analytics event: $name, params: $params")
    }

    override fun setUserProperty(name: String, value: String) {
        // Implementación para iOS
        println("Set user property: $name = $value")
    }
}