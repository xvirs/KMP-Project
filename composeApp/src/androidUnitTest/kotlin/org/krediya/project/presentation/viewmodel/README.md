# Tests para ViewModels

Esta carpeta contiene tests unitarios para los ViewModels de la aplicación.

## Principios Clave

1. **Testear Transformaciones de Datos**: Verificar que los datos del dominio se transforman correctamente en estados de UI.
2. **Testear Manejo de Errores**: Verificar que los errores se capturan y presentan correctamente.
3. **Testear Navegación**: Verificar que se invoquen las acciones de navegación cuando corresponda.
4. **Testear Interacciones con Casos de Uso**: Verificar que los casos de uso se llamen con los parámetros correctos.

## Ejemplo de Estructura de Test

```kotlin
class ExampleViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<ExampleUseCase>()
    private lateinit var viewModel: ExampleViewModel
    
    @Before
    fun setUp() {
        viewModel = ExampleViewModel(useCase)
    }
    
    @Test
    fun `loadData sets Success state when use case returns success`() = runTest {
        // Given
        coEvery { useCase.invoke() } returns StatusResult.Success(testData)
        
        // When
        viewModel.loadData()
        advanceUntilIdle() // Avanzar el tiempo hasta que todas las corrutinas estén completas
        
        // Then
        val state = viewModel.uiState.value
        assertTrue(state is UIState.Success)
        assertEquals(expectedData, (state as UIState.Success<YourDataType>).data)
    }
}