package org.krediya.project.shared.analytics

interface AnalyticsService {
    fun logEvent(name: String, params: Map<String, Any> = emptyMap())
    fun setUserProperty(name: String, value: String)
}