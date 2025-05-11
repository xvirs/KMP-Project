package org.krediya.project.shared.analytics

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics

actual open class FirebaseAnalyticsService : AnalyticsService {
    private val analytics = Firebase.analytics

    actual override fun logEvent(name: String, params: Map<String, Any>) {

        val bundle = Bundle()
        params.forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> bundle.putString(key, value.toString())
            }
        }
        analytics.logEvent(name, bundle)
    }

    actual override fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }
}