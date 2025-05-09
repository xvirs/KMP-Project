// AnalyticsFactory.kt en androidMain
package org.krediya.project.shared.analytics

actual fun createPlatformAnalyticsFactory(): AnalyticsFactory = AndroidAnalyticsFactory()

class AndroidAnalyticsFactory : AnalyticsFactory {
    override fun createAnalyticsService(): AnalyticsService = FirebaseAnalyticsService()
    override fun createCrashlyticsService(): CrashlyticsService = FirebaseCrashlyticsService()
}