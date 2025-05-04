package org.krediya.project.shared.analytics

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

actual class FirebaseCrashlyticsService : CrashlyticsService {
    private val crashlytics = Firebase.crashlytics

    override fun logException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun setUserId(id: String) {
        crashlytics.setUserId(id)
    }
}