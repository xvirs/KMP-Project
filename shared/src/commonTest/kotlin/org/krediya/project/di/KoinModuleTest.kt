package org.krediya.project.di

import io.ktor.client.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.get
import org.krediya.project.data.datasource.PostsDataSourceImpl
import org.krediya.project.data.interfaces.PostsDataSourceInterface
import org.krediya.project.data.network.BaseClient
import org.krediya.project.data.repository.PostRepositoryImpl
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.domain.usecase.GetPostsUseCase
import org.krediya.project.shared.analytics.AnalyticsManager
import org.krediya.project.shared.analytics.AnalyticsService
import org.krediya.project.shared.analytics.CrashlyticsService
import kotlin.test.*

/**
 * Pruebas para la configuración de los módulos de Koin
 */
class KoinModuleTest : KoinTest {

    @BeforeTest
    fun setup() {
        // Iniciar Koin con los módulos que queremos probar
        startKoin {
            modules(
                commonModule,
                mockPlatformModule(),
                testAnalyticsModule(),
                )
        }
    }

    @AfterTest
    fun tearDown() {
        // Detener Koin después de cada prueba
        stopKoin()
    }

    /**
     * Verifica que todos los módulos Koin pueden ser resueltos correctamente
     */
    @Test
    fun testKoinModulesConfiguration() {
        // Detiene cualquier instancia activa de Koin antes del test
        tearDown()

        checkModules {
            modules(commonModule, mockPlatformModule(), testAnalyticsModule())
        }
    }
    /**
     * Verifica que los servicios de Analytics y Crashlytics están configurados correctamente
     */
    @Test
    fun testAnalyticsServicesInjection() {
        // Obtener instancias de los servicios
        val analyticsService = get<AnalyticsService>()
        val crashlyticsService = get<CrashlyticsService>()
        val analyticsManager = get<AnalyticsManager>()

        // Verificar que las instancias no son nulas
        assertNotNull(analyticsService)
        assertNotNull(crashlyticsService)
        assertNotNull(analyticsManager)
    }

    /**
     * Verifica que los componentes de red están configurados correctamente
     */
    @Test
    fun testNetworkComponentsInjection() {
        // Obtener instancias de los componentes de red
        val httpClient = get<HttpClient>()
        val baseClient = get<BaseClient>()

        // Verificar que las instancias no son nulas
        assertNotNull(httpClient)
        assertNotNull(baseClient)
    }

    /**
     * Verifica que los componentes de datos están configurados correctamente
     */
    @Test
    fun testDataComponentsInjection() {
        // Obtener instancias de los componentes de datos
        val postsDataSource = get<PostsDataSourceInterface>()
        val postRepository = get<PostRepository>()

        // Verificar que las instancias no son nulas
        assertNotNull(postsDataSource)
        assertNotNull(postRepository)

        // Verificar que son de los tipos correctos
        assertTrue(postsDataSource is PostsDataSourceImpl)
        assertTrue(postRepository is PostRepositoryImpl)
    }

    /**
     * Verifica que los casos de uso están configurados correctamente
     */
    @Test
    fun testUseCasesInjection() {
        // Obtener instancia del caso de uso
        val getPostsUseCase = get<GetPostsUseCase>()

        // Verificar que la instancia no es nula
        assertNotNull(getPostsUseCase)
    }

    /**
     * Verifica el grafo completo de dependencias para GetPostsUseCase
     */
    @Test
    fun testGetPostsUseCaseDependencyGraph() {
        // Obtener instancia del caso de uso
        val getPostsUseCase = get<GetPostsUseCase>()

        // Obtener sus dependencias
        val postRepository = get<PostRepository>()

        // Verificar que el caso de uso tiene las dependencias correctas
        assertEquals(postRepository, getPostsUseCase.postRepository)
    }
}

/**
 * Módulo de plataforma simulado para pruebas
 */
fun mockPlatformModule() = org.koin.dsl.module {
    // Aquí puedes añadir dependencias simuladas específicas de la plataforma si es necesario
}