package com.example.myfinances2020.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myfinances2020.repository.database.getDatabase
import com.example.myfinances2020.utils.ENDPOINT
import com.example.myfinances2020.utils.SHARED_PREFS
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideRetrofitInstance(converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabaseInstance(application: Application) = getDatabase(application)

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences = application.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
}