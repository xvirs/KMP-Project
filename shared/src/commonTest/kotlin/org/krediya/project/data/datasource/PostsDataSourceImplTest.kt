package org.krediya.project.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.krediya.project.data.network.BaseClient
import org.krediya.project.data.network.HttpStatus
import org.krediya.project.util.StatusResult
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PostsDataSourceImplIntegrationTest {

    private lateinit var mockEngine: MockEngine
    private lateinit var testableBaseClient: TestableBaseClient
    private lateinit var dataSource: PostsDataSourceImpl

    @BeforeTest
    fun setup() {
        mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/posts" -> {
                    respond(
                        content = ByteReadChannel("""[
                            {"userId": 1, "id": 1, "title": "Test Title 1", "body": "Test Body 1"},
                            {"userId": 2, "id": 2, "title": "Test Title 2", "body": "Test Body 2"}
                        ]"""),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                "/error" -> {
                    respond(
                        content = ByteReadChannel("Error"),
                        status = HttpStatusCode.InternalServerError,
                        headers = headersOf(HttpHeaders.ContentType, "text/plain")
                    )
                }
                "/invalid-json" -> {
                    respond(
                        content = ByteReadChannel("{ this is not valid JSON }"),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> error("Unhandled ${request.url}")
            }
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        testableBaseClient = TestableBaseClient(client)
        dataSource = PostsDataSourceImpl(testableBaseClient)
    }

    @Test
    fun `getPosts should return success with posts list when API call is successful`() = runTest {
        // Ejecutar el método bajo prueba
        val result = dataSource.getPosts()

        // Verificar resultado
        assertTrue(result is StatusResult.Success)
        val posts = (result).data
        assertEquals(2, posts.size)
        assertEquals(1, posts[0].id)
        assertEquals("Test Title 1", posts[0].title)
        assertEquals(2, posts[1].id)
        assertEquals("Test Title 2", posts[1].title)
    }

    @Test
    fun `getPosts should return error when API call fails`() = runTest {
        // Modificar la URL para que falle
        testableBaseClient.forceEndpoint = "/error"

        // Ejecutar el método bajo prueba
        val result = dataSource.getPosts()

        // Verificar resultado
        assertTrue(result is StatusResult.Error)
        assertTrue((result).message.isNotEmpty())
    }

    @Test
    fun `getPosts should return error when JSON parsing fails`() = runTest {
        // Modificar la URL para que devuelva JSON inválido
        testableBaseClient.forceEndpoint = "/invalid-json"

        // Ejecutar el método bajo prueba
        val result = dataSource.getPosts()

        // Verificar resultado
        assertTrue(result is StatusResult.Error)
        assertTrue((result as StatusResult.Error).message.contains("Error de parseo"))
    }

    // Clase para poder controlar el BaseClient en las pruebas
    class TestableBaseClient(private val client: HttpClient) : BaseClient() {
        var forceEndpoint: String? = null

        override fun createClient(): HttpClient = client

        override suspend fun get(endpoint: String, errorMessage: String): HttpStatus {
            return super.get(forceEndpoint ?: endpoint, errorMessage)
        }
    }
}