package org.krediya.project.shared.analytics

expect class FirebaseCrashlyticsService() : CrashlyticsService {
    override fun logException(throwable: Throwable)
    override fun log(message: String)
    override fun setUserId(id: String)
}