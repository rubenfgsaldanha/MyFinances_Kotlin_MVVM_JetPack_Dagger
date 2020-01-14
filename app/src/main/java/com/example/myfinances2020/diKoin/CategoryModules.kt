package com.example.myfinances2020.diKoin

import com.example.myfinances2020.repository.CategoryRepository
import com.example.myfinances2020.repository.database.MyFinancesDatabase
import com.example.myfinances2020.repository.database.daos.CategoryDao
import com.example.myfinances2020.repository.network.categories.CategoryDataSource
import com.example.myfinances2020.repository.network.categories.CategoryService
import org.koin.dsl.module
import retrofit2.Retrofit

val categoriesModule = module {
    fun provideCategoryService(retrofit: Retrofit): CategoryService = retrofit.create(
        CategoryService::class.java)
    fun provideCategoryDao(database: MyFinancesDatabase): CategoryDao = database.categoryDao

    single { provideCategoryService(get()) }
    single { provideCategoryDao(get()) }
}

val categoryRepositoryModule = module {
    single {
        CategoryRepository(
            get(),
            get()
        )
    }
}

val categoryDataSourceModule = module {
    single { CategoryDataSource(get()) }
}