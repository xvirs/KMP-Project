package org.krediya.project.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

open class BaseClient {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    protected open fun createClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    private val client by lazy { createClient() }

    open suspend fun get(
        endpoint: String,
        errorMessage: String
    ): HttpStatus {
        return executeRequest(
            requestCall = { client.get("$BASE_URL$endpoint") },
            errorMessage = errorMessage
        )
    }

    open suspend fun post(
        endpoint: String,
        body: Any,
        errorMessage: String
    ): HttpStatus {
        return executeRequest(
            requestCall = {
                client.post("$BASE_URL$endpoint") {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            },
            errorMessage = errorMessage
        )
    }

    /**
     * Función que modulariza la ejecución de peticiones HTTP y el manejo de errores
     */
    private suspend fun executeRequest(
        requestCall: suspend () -> HttpResponse,
        errorMessage: String
    ): HttpStatus {
        return try {
            val response = requestCall()
            handleResponse(response, errorMessage)
        } catch (e: ClientRequestException) {
            handleClientException(e)
        } catch (e: Exception) {
            HttpStatus(errorMessage = e.message.toString(), errorType = ErrorType.NETWORK)
        }
    }

    /**
     * Función para manejar la respuesta HTTP
     */
    private fun handleResponse(response: HttpResponse, errorMessage: String): HttpStatus {
        return if (response.status.value in 200..299) {
            HttpStatus(httpResponse = response)
        } else {
            handleErrorResponse(response.status.value, errorMessage)
        }
    }

    /**
     * Función para manejar respuestas de error
     */
    private fun handleErrorResponse(statusCode: Int, errorMessage: String): HttpStatus {
        return if (statusCode in 400..499) {
            HttpStatus(errorMessage = errorMessage, errorType = ErrorType.CLIENT)
        } else {
            HttpStatus(errorMessage = errorMessage, errorType = ErrorType.SERVER)
        }
    }

    /**
     * Función para manejar excepciones del cliente
     */
    private fun handleClientException(e: ClientRequestException): HttpStatus {
        return if (e.response.status.value in 400..499) {
            HttpStatus(errorMessage = "Error del cliente: ${e.message}", errorType = ErrorType.CLIENT)
        } else {
            HttpStatus(errorMessage = e.message, errorType = ErrorType.NETWORK)
        }
    }
}

data class HttpStatus(
    val httpResponse: HttpResponse? = null,
    val errorMessage: String = "",
    val errorType: ErrorType? = null
)

enum class ErrorType {
    NETWORK,
    SERVER,
    CLIENT
}