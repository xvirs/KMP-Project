package org.krediya.project

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.krediya.project.di.commonModule
import org.krediya.project.di.platformModule
import org.krediya.project.viewmodel.PostViewModel

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

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
        viewModel { PostViewModel(get()) }
    }
}