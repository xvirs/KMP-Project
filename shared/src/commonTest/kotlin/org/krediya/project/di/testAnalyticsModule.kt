package org.krediya.project.di

import org.koin.dsl.module
import org.krediya.project.shared.analytics.AnalyticsManager
import org.krediya.project.shared.analytics.AnalyticsService
import org.krediya.project.shared.analytics.CrashlyticsService

/**
 * Módulo de analytics para pruebas
 * Proporciona implementaciones simuladas de los servicios de analytics
 */
fun testAnalyticsModule() = module {
    // Servicio de Analytics simulado
    single<AnalyticsService> {
        object : AnalyticsService {
            override fun logEvent(eventName: String, params: Map<String, Any>) {
                // No hace nada en las pruebas
            }

            override fun setUserProperty(name: String, value: String) {
                // No hace nada en las pruebas
            }

            // Implementa otros métodos requeridos si es necesario
        }
    }

    // Servicio de Crashlytics simulado
    single<CrashlyticsService> {
        object : CrashlyticsService {
            override fun logException(throwable: Throwable) {
                TODO("Not yet implemented")
            }

            override fun log(message: String) {
                TODO("Not yet implemented")
            }

            override fun setUserId(id: String) {
                TODO("Not yet implemented")
            }

            // Implementa otros métodos requeridos si es necesario
        }
    }

    // Manager de Analytics que usa los servicios simulados
    single {
        AnalyticsManager(get(), get())
    }
}