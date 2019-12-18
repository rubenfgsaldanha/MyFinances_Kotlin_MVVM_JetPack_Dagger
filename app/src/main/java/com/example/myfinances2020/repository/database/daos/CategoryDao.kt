package com.example.myfinances2020.repository.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfinances2020.repository.database.entities.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: Category)

    @Insert
    suspend fun insertAll(vararg categories: Category)

    @Update
    suspend fun update(category: Category)

    @Query("select * from categories")
    fun getAllCategories() : LiveData<List<Category>>

    @Query("select * from categories where _id = :id")
    fun getCategoryById(id: Long) : Category

    @Query("select * from categories where label = :label")
    fun getCategoryByLabel(label: String)

    @Query("select label from categories")
    fun getAllLabels() : List<String>

    @Query("delete from categories where label = :label")
    suspend fun deleteCategory(label: String)
}