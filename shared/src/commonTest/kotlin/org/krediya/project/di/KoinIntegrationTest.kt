package org.krediya.project.di

import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.domain.usecase.GetPostsUseCase
import org.krediya.project.util.StatusResult
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Prueba de integración para verificar que el grafo de dependencias funciona correctamente
 * Esta prueba simula un flujo completo de la aplicación usando las dependencias inyectadas
 */
class KoinIntegrationTest : KoinTest {

    // Inyección lazy de las dependencias que vamos a usar
    private val getPostsUseCase: GetPostsUseCase by inject()
    private val postRepository: PostRepository by inject()

    @BeforeTest
    fun setup() {
        // Iniciar Koin con los módulos comunes y el módulo de plataforma simulado para pruebas
        startKoin {
            modules(commonModule, testPlatformModule())
        }
    }

    @AfterTest
    fun tearDown() {
        // Detener Koin después de cada prueba
        stopKoin()
    }

    /**
     * Prueba que verifica el flujo completo desde el caso de uso hasta la fuente de datos
     */
    @Test
    fun testCompleteFlowWithInjectedDependencies() = runTest {
        // Verificar que podemos obtener las dependencias correctamente
        assertNotNull(getPostsUseCase)
        assertNotNull(postRepository)

        // Ejecutar el caso de uso
        val result = getPostsUseCase()

        // Verificar que la respuesta es del tipo correcto
        when (result) {
            is StatusResult.Success -> {
                assertTrue(result.data.isNotEmpty(), "La lista de posts está vacía")
            }
            is StatusResult.Error -> {
                error("Se esperaba una respuesta exitosa pero se obtuvo un error: ${result.message}")
            }
        }
    }
}
