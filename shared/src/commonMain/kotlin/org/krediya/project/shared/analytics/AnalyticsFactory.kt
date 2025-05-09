// AnalyticsFactory.kt en commonMain
package org.krediya.project.shared.analytics

interface AnalyticsFactory {
    fun createAnalyticsService(): AnalyticsService
    fun createCrashlyticsService(): CrashlyticsService
}

expect fun createPlatformAnalyticsFactory(): AnalyticsFactory