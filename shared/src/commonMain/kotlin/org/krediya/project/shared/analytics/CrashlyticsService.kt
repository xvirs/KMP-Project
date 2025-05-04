package org.krediya.project.shared.analytics

interface CrashlyticsService {
    fun logException(throwable: Throwable)
    fun log(message: String)
    fun setUserId(id: String)
}