import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * Archivo de configuración de Gradle para el módulo shared.
 * Define la configuración del proyecto multiplatforma para Android e iOS.
 */
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    // Configuración para Android
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // Configuración para iOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    // Configuración de los source sets y sus dependencias
    sourceSets {
        // Dependencias comunes para pruebas
        commonTest.dependencies {
            implementation("io.insert-koin:koin-test:3.5.0")
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.common)
            implementation(libs.mockk.common)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.slf4j.simple)
            implementation(libs.turbine)
            implementation(libs.ktor.client.mock)
        }

        // Dependencias comunes para la implementación principal
        commonMain.dependencies {
            // Serialización
            implementation(libs.kotlinx.serialization.json)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            // Koin
            implementation(libs.koin.core)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }

        // Dependencias específicas para pruebas de Android
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
            implementation(libs.mockk)
        }

        // Dependencias específicas para Android
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.crashlytics.ktx)
        }

        // Dependencias específicas para iOS
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        // Dependencias específicas para pruebas de iOS
        iosTest.dependencies {
            implementation(libs.mockk.common)
        }
    }
}

// Configuración de Android
android {
    namespace = "org.krediya.project.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

// Dependencias del proyecto a nivel de módulo
dependencies {
    implementation(libs.play.services.measurement.api)
    implementation(libs.firebase.common.ktx)
}