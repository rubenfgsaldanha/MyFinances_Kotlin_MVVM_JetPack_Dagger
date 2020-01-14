package com.example.myfinances2020

import android.app.Application
import com.example.myfinances2020.diKoin.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KoinApplication)
            modules(getAllModules())
        }
    }

    private fun getAllModules() = listOf(
        viewModelsModule,
        transactionsModule,
        loansModule,
        categoriesModule,
        retrofitModule,
        databaseModule,
        sharedPreferencesModule
    )
}