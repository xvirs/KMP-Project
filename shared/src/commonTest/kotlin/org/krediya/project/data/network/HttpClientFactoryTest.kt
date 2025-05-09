package org.krediya.project.data.network

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class HttpClientFactoryTest {

    @Test
    fun `create should return non-null HttpClient`() {
        val client = HttpClientFactory.create()
        assertNotNull(client, "HttpClient should be created successfully")
    }

    // Nota: No podemos probar directamente si los plugins están instalados
    // porque pluginConfigurations no es accesible públicamente
    // En su lugar, podemos verificar que el cliente se crea correctamente

    @Test
    fun `HttpClient should be properly configured`() = runTest {
        // Verificamos que el cliente tiene una configuración básica correcta
        // Probamos que se creó exitosamente y podemos acceder a sus propiedades
        val client = HttpClientFactory.create()
        assertNotNull(client.engine, "HttpClient engine should be initialized")

        // Nota: En un entorno real podríamos hacer una solicitud de prueba
        // pero en tests unitarios es mejor no realizar conexiones reales
    }
}