package org.krediya.project.shared.analytics

class AnalyticsManager(
    private val analytics: AnalyticsService,
    private val crashlytics: CrashlyticsService
) {
    fun trackScreen(screenName: String) {
        analytics.logEvent("screen_view", mapOf("screen_name" to screenName))
    }

    fun trackEvent(eventName: String, params: Map<String, Any> = emptyMap()) {
        analytics.logEvent(eventName, params)
    }

    fun trackError(error: Throwable, message: String? = null) {
        crashlytics.logException(error)
        message?.let { crashlytics.log(it) }
    }

    fun setUser(userId: String) {
        crashlytics.setUserId(userId)
    }
}