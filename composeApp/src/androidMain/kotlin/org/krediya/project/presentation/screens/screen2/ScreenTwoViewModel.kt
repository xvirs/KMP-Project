package org.krediya.project.presentation.screens.screen2

import androidx.lifecycle.ViewModel
import org.krediya.project.shared.analytics.AnalyticsManager

class ScreenTwoViewModel(
    private val analyticsManager: AnalyticsManager
) : ViewModel() {



    fun testCrashlytics() {
        throw RuntimeException("Test Exception")
    }

    fun testAnalytics() {
        // Simular una vista de pantalla
        analyticsManager.trackScreen("ScreenTwo")

        // Simular un evento personalizado
        analyticsManager.trackEvent(
            eventName = "button_clicked",
            params = mapOf(
                "button_name" to "test_button",
                "clicked_at" to System.currentTimeMillis()
            )
        )
    }


}