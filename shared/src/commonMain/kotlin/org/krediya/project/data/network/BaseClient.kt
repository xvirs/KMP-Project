package org.krediya.project.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BaseClient() {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun get(
        endpoint: String,
        errorMessage: String
    ): HttpStatus {
        return try {
            val response: HttpResponse = client.get("$BASE_URL$endpoint")

            if (response.status.value in 200..299) {
                HttpStatus(httpResponse = response)
            } else {
                HttpStatus(errorMessage = errorMessage, errorType = ErrorType.SERVER)
            }
        } catch (e: ClientRequestException) {
            if (e.response.status.value in 400..499) {
                HttpStatus(errorMessage = "Error del cliente: ${e.message}", errorType = ErrorType.CLIENT)
            } else {
                HttpStatus(errorMessage = e.message.toString(), errorType = ErrorType.NETWORK)
            }
        } catch (e: Exception) {
            HttpStatus(errorMessage = e.message.toString(), errorType = ErrorType.NETWORK)
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