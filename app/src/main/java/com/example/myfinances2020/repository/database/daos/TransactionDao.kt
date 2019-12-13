package com.example.myfinances2020.repository.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myfinances2020.repository.database.entities.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg transactions: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Query("select * from transactions order by _id desc")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("select * from transactions WHERE _id = :id")
    fun getTransactionById(id: Long): LiveData<Transaction>

    @Query("select * from transactions where transaction_month = :month and transaction_year = :year order by transaction_day desc")
    fun getTransactionsByMonth(month: Int, year: Int): LiveData<List<Transaction>>

    @Query("delete from transactions where _id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Query("delete from transactions")
    suspend fun clear()
}