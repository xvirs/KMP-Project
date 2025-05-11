package org.krediya.project.utils

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

/**
 * Extensiones útiles para simplificar los tests de Compose UI.
 * Estas funciones pueden utilizarse en tests unitarios o instrumentados
 * que usen ComposeTestRule.
 */

/**
 * Encuentra un nodo con el texto especificado y realiza un clic.
 * @param text El texto exacto a buscar
 * @return La interacción con el nodo para encadenar acciones adicionales
 */
fun ComposeContentTestRule.clickOnNodeWithText(text: String): SemanticsNodeInteraction {
    return onNodeWithText(text).performClick()
}

/**
 * Encuentra un nodo con el testTag especificado y realiza un clic.
 * @param testTag El testTag asignado al componente
 * @return La interacción con el nodo para encadenar acciones adicionales
 */
fun ComposeContentTestRule.clickOnNodeWithTag(testTag: String): SemanticsNodeInteraction {
    return onNodeWithTag(testTag).performClick()
}

/**
 * Espera a que aparezca un elemento con el texto especificado o hasta que
 * se agote el tiempo de espera.
 * @param text El texto a buscar
 * @param timeoutMillis Tiempo máximo de espera en milisegundos
 */
fun ComposeContentTestRule.waitForNodeWithText(text: String, timeoutMillis: Long = 5000) {
    this.waitUntil(timeoutMillis = timeoutMillis) {
        try {
            onNodeWithText(text).fetchSemanticsNode()
            true
        } catch (e: Exception) {
            false
        }
    }
}

/**
 * Espera a que aparezca un elemento con el testTag especificado o hasta que
 * se agote el tiempo de espera.
 * @param testTag El testTag a buscar
 * @param timeoutMillis Tiempo máximo de espera en milisegundos
 */
fun ComposeContentTestRule.waitForNodeWithTag(testTag: String, timeoutMillis: Long = 5000) {
    this.waitUntil(timeoutMillis = timeoutMillis) {
        try {
            onNodeWithTag(testTag).fetchSemanticsNode()
            true
        } catch (e: Exception) {
            false
        }
    }
}