package org.krediya.project.data.network

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.pluginOrNull
import kotlin.test.Test
import kotlin.test.assertTrue

class HttpClientFactoryTest {

    @Test
    fun `create deberia devolver HttpClient con plugins ContentNegotiation y Logging`() {
        // Cuando
        val client = HttpClientFactory.create()

        // Entonces
        assertTrue(client.pluginOrNull(ContentNegotiation) != null, "El plugin ContentNegotiation debería estar instalado")
        assertTrue(client.pluginOrNull(Logging) != null, "El plugin Logging debería estar instalado")
    }
}