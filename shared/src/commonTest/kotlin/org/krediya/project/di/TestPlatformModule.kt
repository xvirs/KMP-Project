// Para pruebas (commonTest)
// Archivo: commonTest/kotlin/org/krediya/project/di/TestPlatformModule.kt
package org.krediya.project.di

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Implementación del módulo de plataforma para pruebas
 * Sobrescribe las dependencias reales con mocks para las pruebas
 */
fun testPlatformModule(): Module = module {
    // Cliente HTTP simulado para pruebas
    single<HttpClient> {
        HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when (request.url.encodedPath) {
                        "/posts" -> {
                            respond(
                                content = ByteReadChannel("""[{"userId": 1, "id": 1, "title": "Test Post", "body": "Test Content"}]"""),
                                status = HttpStatusCode.OK,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        }
                        else -> {
                            respond(
                                content = ByteReadChannel("{}"),
                                status = HttpStatusCode.OK,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    // Aquí puedes añadir más mocks para otras dependencias específicas de la plataforma
}