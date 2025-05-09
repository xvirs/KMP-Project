import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * Archivo de configuración de Gradle para el módulo composeApp.
 * Define la configuración del proyecto multiplatforma con enfoque en Compose.
 */
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        // Configuración de dependencias para Android Main
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.material3)
            implementation(libs.androidx.navigation.compose)
        }

        // Configuración de dependencias comunes
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)
        }

        // Configuración para tests de Android instrumentados
        androidInstrumentedTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.krediya.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.krediya.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
            all {
                it.jvmArgs("-Duser.language=en")
            }
        }
    }
}

// Configuración de dependencias externas no incluidas en sourceSets
dependencies {
    // Firebase y Google Play Services
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.measurement)
    implementation(libs.play.services.measurement.base)
    implementation(libs.play.services.measurement.sdk)
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.androidx.navigation.testing) // Podría moverse a testImplementation

    // Dependencias de testing
    // JUnit y Mockk
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)

    // Turbine para testing de flows
    testImplementation(libs.turbine)

    // Coroutines para pruebas
    testImplementation(libs.kotlinx.coroutines.test.v180)

    // Koin para pruebas
    implementation(libs.koin.test)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.koin.androidx.compose)

    // Mockito Kotlin
    testImplementation(libs.mockito.kotlin)

    // Compose UI Testing
    testImplementation(libs.androidx.ui.test)
    testImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Robolectric para tests que requieran contexto Android
    testImplementation(libs.robolectric)

    // Las siguientes dependencias deberían estar solo en testImplementation
    // y ya están incluidas arriba o en commonMain/androidMain
    // NO es necesario incluirlas aquí de nuevo:
    // implementation(libs.koin.test) - Ya está en testImplementation
    // implementation(libs.kotlin.test) - Ya está en testImplementation
    // implementation(libs.kotlin.test.common) - Redundante
    // implementation(libs.mockk.common) - Redundante con mockk
    // implementation(libs.kotlinx.coroutines.test) - Ya está en testImplementation
    // implementation(libs.slf4j.simple) - Solo necesaria para tests
    // implementation(libs.turbine) - Ya está en testImplementation
    // implementation(libs.ktor.client.mock) - Solo necesaria para tests
}