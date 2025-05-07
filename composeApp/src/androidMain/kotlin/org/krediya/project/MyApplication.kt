package org.krediya.project

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.krediya.project.di.commonModule
import org.krediya.project.di.platformModule
import org.krediya.project.presentation.screens.screen1.ScreenOneViewModel
import org.krediya.project.presentation.screens.screen2.ScreenTwoViewModel
import org.krediya.project.presentation.screens.screen3.ScreenThreeViewModel

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        // Inicializar Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                commonModule,
                platformModule(),
                viewModelModule
            )
        }
    }

    private val viewModelModule = module {
        viewModel { ScreenOneViewModel(get()) }
        viewModel { ScreenTwoViewModel(get()) }
        viewModel { ScreenThreeViewModel() }
    }
}