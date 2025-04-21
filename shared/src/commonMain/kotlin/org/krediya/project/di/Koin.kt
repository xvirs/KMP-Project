package org.krediya.project.di


import io.ktor.client.*
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.krediya.project.data.repository.PostRepositoryImpl
import org.krediya.project.data.source.remote.HttpClientFactory
import org.krediya.project.data.source.remote.PostApiService
import org.krediya.project.domain.repository.PostRepository
import org.krediya.project.domain.usecase.GetPostsUseCase

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, platformModule())
}

// Módulo común para ambas plataformas
val commonModule = module {
    // HTTP Client
    single<HttpClient> { HttpClientFactory.create() }

    // API Service
    single { PostApiService(get()) }

    // Repository
    single<PostRepository> { PostRepositoryImpl(get()) }

    // Use Cases
    factory { GetPostsUseCase(get()) }
}

// Para inicializar Koin desde Kotlin (si es necesario)
fun initKoin() = initKoin {}

// Funciones expect/actual para módulos específicos de plataforma
expect fun platformModule(): Module