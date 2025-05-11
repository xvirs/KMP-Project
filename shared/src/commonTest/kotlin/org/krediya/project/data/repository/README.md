# Tests para Repositorios

Esta carpeta contendrá los tests unitarios para las implementaciones de repositorios.

## Qué testear

Para cada repositorio implementado, se deben incluir tests que:

1. **Verifiquen el mapeo** de modelos de datos a modelos de dominio
2. **Verifiquen el manejo de errores** de las fuentes de datos
3. **Verifiquen la lógica de caché** (si aplica)

## Convención de nombres

Los archivos de test deben seguir la convención:

- `<NombreRepositorio>Test.kt`

Ejemplo: `PostRepositoryImplTest.kt`

## Ejemplo de estructura de test

```kotlin
class PostRepositoryImplTest {
    // Setup

    @Test
    fun `método devuelve datos correctamente cuando datasource responde con éxito`() {
        // Given

        // When

        // Then
    }

    @Test
    fun `método devuelve error cuando datasource falla`() {
        // Given

        // When

        // Then
    }
}