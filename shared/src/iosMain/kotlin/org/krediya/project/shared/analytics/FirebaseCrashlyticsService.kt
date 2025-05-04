package org.krediya.project.shared.analytics

actual class FirebaseCrashlyticsService : CrashlyticsService {
    override fun logException(throwable: Throwable) {
        // Implementación para iOS
        println("Crashlytics exception: ${throwable.message}")
    }

    override fun log(message: String) {
        // Implementación para iOS
        println("Crashlytics log: $message")
    }

    override fun setUserId(id: String) {
        // Implementación para iOS
        println("Crashlytics user ID: $id")
    }
}