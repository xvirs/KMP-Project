package org.krediya.project.di

import io.ktor.client.*
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.krediya.project.data.datasource.PostsDataSourceImpl
import org.krediya.project.data.repository.PostRepositoryImpl
import org.krediya.project.data.network.HttpClientFactory
import org.krediya.project.data.interfaces.PostsDataSourceInterface
import org.krediya.project.data.network.BaseClient
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.domain.usecase.GetPostsUseCase
import org.krediya.project.shared.analytics.AnalyticsManager
import org.krediya.project.shared.analytics.AnalyticsService
import org.krediya.project.shared.analytics.CrashlyticsService
import org.krediya.project.shared.analytics.FirebaseAnalyticsService
import org.krediya.project.shared.analytics.FirebaseCrashlyticsService
import org.krediya.project.shared.analytics.createPlatformAnalyticsFactory

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, platformModule())
}

// Módulo común para ambas plataformas
val commonModule = module {

    // Analytics y Crashlytics
    single { createPlatformAnalyticsFactory() }
    single<AnalyticsService> { FirebaseAnalyticsService() }
    single<CrashlyticsService> { FirebaseCrashlyticsService() }
    single { AnalyticsManager(get(), get()) }

    // HTTP Client
    single<HttpClient> { HttpClientFactory.create() }

    // BaseClient
    single { BaseClient() }

    // DataSource
    single<PostsDataSourceInterface> { PostsDataSourceImpl(get()) }

    // Repository
    single<PostRepository> { PostRepositoryImpl(get()) }

    // Use Cases
    factory { GetPostsUseCase(get()) }
}

// Para inicializar Koin desde Kotlin (si es necesario)
fun initKoin() = initKoin {}

// Funciones expect/actual para módulos específicos de plataforma
expect fun platformModule(): Module