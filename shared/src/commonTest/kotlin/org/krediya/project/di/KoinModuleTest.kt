package org.krediya.project.di

import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.krediya.project.data.network.BaseClient
import org.krediya.project.data.network.HttpClientFactory
import org.krediya.project.shared.analytics.AnalyticsManager
import org.krediya.project.shared.analytics.AnalyticsService
import org.krediya.project.shared.analytics.CrashlyticsService
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class KoinModulesTest : KoinTest {

    // Mock de servicios dependientes de Firebase
    private class MockAnalyticsService : AnalyticsService {
        override fun logEvent(name: String, params: Map<String, Any>) {}
        override fun setUserProperty(name: String, value: String) {}
    }

    private class MockCrashlyticsService : CrashlyticsService {
        override fun logException(throwable: Throwable) {}
        override fun log(message: String) {}
        override fun setUserId(id: String) {}
    }

    // Módulo de test simplificado que solo prueba componentes básicos
    private val testModule = module {
        // HTTP Client
        single<HttpClient> { HttpClientFactory.create() }

        // BaseClient
        single { BaseClient() }

        // Analytics mocks
        single<AnalyticsService> { MockAnalyticsService() }
        single<CrashlyticsService> { MockCrashlyticsService() }
        single { AnalyticsManager(get(), get()) }
    }

    @BeforeTest
    fun setup() {
        // Asegurarse de que Koin no está iniciado
        stopKoin()

        // Inicializar Koin solo con el módulo de prueba
        startKoin {
            modules(testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        // Detener Koin después de cada prueba
        stopKoin()
    }

    @Test
    fun `verificar que las dependencias basicas pueden ser resueltas`() {
        // Verificamos que las dependencias básicas se puedan resolver

        // HTTP Client y BaseClient
        val httpClient = get<HttpClient>()
        assertNotNull(httpClient, "HttpClient debe ser inyectable")

        val baseClient = get<BaseClient>()
        assertNotNull(baseClient, "BaseClient debe ser inyectable")

        // Servicios de Analytics y Crashlytics
        val analyticsService = get<AnalyticsService>()
        assertNotNull(analyticsService, "AnalyticsService debe ser inyectable")

        val crashlyticsService = get<CrashlyticsService>()
        assertNotNull(crashlyticsService, "CrashlyticsService debe ser inyectable")

        val analyticsManager = get<AnalyticsManager>()
        assertNotNull(analyticsManager, "AnalyticsManager debe ser inyectable")
    }
}