import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import java.io.FileInputStream

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
    // Descomenta la siguiente línea si necesitas utilizar Baseline Profiles
    // id("androidx.baselineprofile")
}

/**
 * Cargar propiedades locales para configuración de firma segura
 */
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

/**
 * Resolución de conflictos de versiones.
 * Forzamos versiones específicas para evitar problemas en tiempo de compilación.
 */
configurations.all {
    resolutionStrategy {
        // Forzar versiones específicas de kotlin-test para resolver conflictos
        force("org.jetbrains.kotlin:kotlin-test:1.9.0")
        force("org.jetbrains.kotlin:kotlin-test-common:1.9.0")
        force("org.jetbrains.kotlin:kotlin-test-annotations-common:1.9.0")
        force("org.jetbrains.kotlin:kotlin-test-junit:1.9.0")
    }
}

/**
 * Configuración del compilador Kotlin y fuentes para múltiples plataformas.
 */
kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        // ======== ANDROID MAIN ========
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.material3)
            implementation(libs.androidx.navigation.compose)
        }

        // ======== COMMON MAIN ========
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // AndroidX ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Módulos internos
            implementation(projects.shared)
        }

        // ======== ANDROID INSTRUMENTED TEST ========
        androidInstrumentedTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

/**
 * Configuración específica para Android.
 */
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

    // Verificación de deshabilitar Baseline Profiles para CI/CD
    val disableBaselineProfiles = project.findProperty("disableBaselineProfiles")?.toString()?.toBoolean() ?: false

    buildFeatures {
        // Configuración condicional de compose
        compose = true
    }

    // IMPORTANTE: Las signingConfigs deben definirse ANTES de los buildTypes
    signingConfigs {
        // Configuración de release mejorada para CI/CD
        create("release") {
            // Intentar cargar desde local.properties (para CI/CD) o usar valores predeterminados (para desarrollo local)
            val keystoreFile = localProperties["krediya.keystore.file"]?.toString() ?: "../krediya-keystore.jks"
            storeFile = file(keystoreFile)

            // Preferir valores de local.properties (CI/CD) o usar valores predeterminados
            storePassword = localProperties["krediya.keystore.password"]?.toString() ?: "krediya15"
            keyAlias = localProperties["krediya.key.alias"]?.toString() ?: "krediya"
            keyPassword = localProperties["krediya.key.password"]?.toString() ?: "krediya15"
        }

        // El debug SigningConfig ya existe por defecto, solo lo configuramos
        getByName("debug") {
            storeFile = file("../krediya-keystore.jks")
            storePassword = "krediya15"
            keyAlias = "krediya"
            keyPassword = "krediya15"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")

            // Si estás usando Baseline Profiles y quieres deshabilitarlo en CI/CD:
            if (disableBaselineProfiles) {
                println("Baseline Profiles deshabilitados para el build de Release")
            }
        }

        getByName("debug") {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

/**
 * Dependencias adicionales para el proyecto.
 * Organizadas por categorías para mejor mantenimiento.
 */
dependencies {
    // ======== FIREBASE Y GOOGLE PLAY SERVICES ========
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.measurement)
    implementation(libs.play.services.measurement.base)
    implementation(libs.play.services.measurement.sdk)

    // ======== NAVEGACIÓN Y TESTING UI ========
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.androidx.navigation.testing)

    // ======== TESTING UNITARIO ========
    // JUnit, Mockk y frameworks de testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)

    // Testing de flujos y corrutinas
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test.v180)

    // Koin para testing
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    // API de Koin para runtime
    implementation(libs.koin.test) // Considerar si esto es realmente necesario en runtime

    // Mockito
    testImplementation(libs.mockito.kotlin)

    // ======== TESTING DE UI ========
    // Framework de testing de Compose UI
    testImplementation(libs.androidx.ui.test)
    testImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Robolectric para simulación de Android en JVM
    testImplementation(libs.robolectric)
    implementation(libs.slf4j.simple)

    // Baseline Profile - Descomentar si necesitas utilizar Baseline Profiles
    // baselineProfile(project(":baselineprofile"))
}