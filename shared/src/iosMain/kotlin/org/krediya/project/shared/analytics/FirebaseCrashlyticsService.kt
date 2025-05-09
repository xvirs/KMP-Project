package org.krediya.project.shared.analytics

actual class FirebaseCrashlyticsService : CrashlyticsService {
    actual override fun logException(throwable: Throwable) {
        // Implementación para iOS
        println("Crashlytics exception: ${throwable.message}")
    }

    actual override fun log(message: String) {
        // Implementación para iOS
        println("Crashlytics log: $message")
    }

    actual override fun setUserId(id: String) {
        // Implementación para iOS
        println("Crashlytics user ID: $id")
    }
}