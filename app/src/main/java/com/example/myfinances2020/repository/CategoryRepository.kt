package com.example.myfinances2020.repository

import com.example.myfinances2020.repository.database.daos.CategoryDao
import com.example.myfinances2020.repository.network.Result
import com.example.myfinances2020.repository.network.categories.CategoryDataSource
import com.example.myfinances2020.repository.network.categories.asDatabaseModel

class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val categoryDataSource: CategoryDataSource?
) {

    var categories = categoryDao.getAllCategories()
    var categoryLabels = categoryDao.getAllLabels()

    suspend fun getCategoryByLabel(label: String) = categoryDao.getCategoryByLabel(label)

    suspend fun refreshCategories() {
        categoryDataSource?.let {
            val result = categoryDataSource.getCategories()
            if (result is Result.Success) {
                val networkCategoryList = result.data
                categoryDao.insertAll(*networkCategoryList.asDatabaseModel())
            }
        }
    }
}