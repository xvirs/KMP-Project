package org.krediya.project.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.krediya.project.presentation.screens.screen1.ScreenOne
import org.krediya.project.presentation.theme.MyTemplateTheme

class IntegrationUITest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screenIntegrationTest() {
        // Setup Koin
        stopKoin() // Asegurarse de que no haya una instancia previa
        startKoin {
            modules(
                module {
                    // Aquí configura las dependencias necesarias para la prueba
                }
            )
        }

        // Setup Compose test
        composeTestRule.setContent {
            MyTemplateTheme {
                // Si quieres usar KoinApplication, asegúrate de tener las dependencias correctas
                // Alternativa: simplemente usa el contexto de Koin configurado arriba
                ScreenOne()
            }
        }

        // Verifica comportamiento
        // ...

        // Limpieza
        stopKoin()
    }
}