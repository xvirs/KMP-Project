// AnalyticsFactory.kt en iosMain
package org.krediya.project.shared.analytics

actual fun createPlatformAnalyticsFactory(): AnalyticsFactory = IosAnalyticsFactory()

class IosAnalyticsFactory : AnalyticsFactory {
    override fun createAnalyticsService(): AnalyticsService = FirebaseAnalyticsService()
    override fun createCrashlyticsService(): CrashlyticsService = FirebaseCrashlyticsService()
}