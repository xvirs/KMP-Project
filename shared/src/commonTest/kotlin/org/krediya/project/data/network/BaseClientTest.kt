package org.krediya.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class BaseClientTest {
    private lateinit var mockEngine: MockEngine
    private lateinit var client: HttpClient
    private lateinit var baseClient: BaseClientTestable

    @BeforeTest
    fun setup() {
        mockEngine = MockEngine { request ->
            when {
                // GET requests
                request.method.value == "GET" && request.url.encodedPath == "/posts" -> {
                    respond(
                        content = ByteReadChannel("""[{"userId": 1, "id": 1, "title": "Test", "body": "Content"}]"""),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                request.method.value == "GET" && request.url.encodedPath == "/error-server" -> {
                    respond(
                        content = ByteReadChannel("Internal Server Error"),
                        status = HttpStatusCode.InternalServerError,
                        headers = headersOf(HttpHeaders.ContentType, "text/plain")
                    )
                }
                request.method.value == "GET" && request.url.encodedPath == "/error-client" -> {
                    respond(
                        content = ByteReadChannel("Not Found"),
                        status = HttpStatusCode.NotFound,
                        headers = headersOf(HttpHeaders.ContentType, "text/plain")
                    )
                }
                // POST requests
                request.method.value == "POST" && request.url.encodedPath == "/posts" -> {
                    respond(
                        content = ByteReadChannel("""{"userId": 1, "id": 101, "title": "New Post", "body": "New Content"}"""),
                        status = HttpStatusCode.Created,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                request.method.value == "POST" && request.url.encodedPath == "/error-server" -> {
                    respond(
                        content = ByteReadChannel("Internal Server Error"),
                        status = HttpStatusCode.ServiceUnavailable,
                        headers = headersOf(HttpHeaders.ContentType, "text/plain")
                    )
                }
                request.method.value == "POST" && request.url.encodedPath == "/error-client" -> {
                    respond(
                        content = ByteReadChannel("Bad Request"),
                        status = HttpStatusCode.BadRequest,
                        headers = headersOf(HttpHeaders.ContentType, "text/plain")
                    )
                }
                else -> error("Unhandled ${request.method.value} ${request.url.encodedPath}")
            }
        }

        client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        baseClient = BaseClientTestable(client)
    }

    // Tests para el método GET
    @Test
    fun `GET successful request returns HttpResponse`() = runTest {
        val result = baseClient.get("/posts", "Error")

        assertNotNull(result.httpResponse)
        assertNull(result.errorType)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `GET server error returns error status`() = runTest {
        val result = baseClient.get("/error-server", "Server Error")

        assertNull(result.httpResponse)
        assertEquals(ErrorType.SERVER, result.errorType)
        assertEquals("Server Error", result.errorMessage)
    }

    @Test
    fun `GET client error returns error status`() = runTest {
        val result = baseClient.get("/error-client", "Client Error")

        assertNull(result.httpResponse)
        assertEquals(ErrorType.CLIENT, result.errorType)
        assertEquals("Client Error", result.errorMessage)
    }

    // Tests para el método POST
    @Serializable
    data class TestPost(val title: String, val body: String, val userId: Int)

    @Test
    fun `POST successful request returns HttpResponse`() = runTest {
        val testPost = TestPost(title = "New Post", body = "New Content", userId = 1)
        val result = baseClient.post("/posts", testPost, "Error")

        assertNotNull(result.httpResponse)
        assertNull(result.errorType)
        assertEquals("", result.errorMessage)
        assertEquals(HttpStatusCode.Created, result.httpResponse?.status)
    }

    @Test
    fun `POST server error returns error status`() = runTest {
        val testPost = TestPost(title = "New Post", body = "New Content", userId = 1)
        val result = baseClient.post("/error-server", testPost, "Server Error")

        assertNull(result.httpResponse)
        assertEquals(ErrorType.SERVER, result.errorType)
        assertEquals("Server Error", result.errorMessage)
    }

    @Test
    fun `POST client error returns error status`() = runTest {
        val testPost = TestPost(title = "New Post", body = "New Content", userId = 1)
        val result = baseClient.post("/error-client", testPost, "Client Error")

        assertNull(result.httpResponse)
        assertEquals(ErrorType.CLIENT, result.errorType)
        assertEquals("Client Error", result.errorMessage)
    }

    // Test para comprobar el manejo de excepciones de red
    @Test
    fun `network exception returns NETWORK error type`() = runTest {
        // Crear un cliente que siempre lance una excepción
        val exceptionClient = BaseClientTestable(HttpClient(MockEngine {
            throw Exception("Network Error")
        }))

        val result = exceptionClient.get("/posts", "Error")

        assertNull(result.httpResponse)
        assertEquals(ErrorType.NETWORK, result.errorType)
        assertEquals("Network Error", result.errorMessage)
    }
}

// Clase testable que permite inyectar un HttpClient mock
class BaseClientTestable(private val httpClient: HttpClient) : BaseClient() {
    override fun createClient(): HttpClient {
        return httpClient
    }
}