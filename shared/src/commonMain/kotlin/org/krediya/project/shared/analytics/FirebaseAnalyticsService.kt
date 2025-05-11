package org.krediya.project.shared.analytics


expect open class FirebaseAnalyticsService() : AnalyticsService {
    override fun logEvent(name: String, params: Map<String, Any>)
    override fun setUserProperty(name: String, value: String)
}