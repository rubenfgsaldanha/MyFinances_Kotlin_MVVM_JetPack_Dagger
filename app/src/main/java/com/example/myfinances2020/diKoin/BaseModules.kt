package com.example.myfinances2020.diKoin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.myfinances2020.repository.database.getDatabase
import com.example.myfinances2020.utils.ENDPOINT
import com.example.myfinances2020.utils.SHARED_PREFS
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    fun provideGson(): Gson = Gson()

    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    fun provideRetrofitInstance(converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(converterFactory)
            .build()
    }

    single { provideGson() }
    single { provideGsonConverterFactory(get()) }
    single { provideRetrofitInstance(get()) }
}

val databaseModule = module {
    fun provideDatabaseInstance(application: Application) = getDatabase(application)

    single { provideDatabaseInstance(androidApplication()) }
}

val sharedPreferencesModule = module {
    fun provideSharedPreferences(application: Application): SharedPreferences = application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    single { provideSharedPreferences(androidApplication()) }
}