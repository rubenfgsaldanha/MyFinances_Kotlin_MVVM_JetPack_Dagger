package com.example.myfinances2020.repository.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myfinances2020.repository.database.entities.Loan

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loan: Loan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg loans: Loan)

    @Update
    suspend fun update(loan: Loan)

    @Query("select * from loans order by _id desc")
    fun getAllLoans(): LiveData<List<Loan>>

    @Query("select * from loans where _id = :id")
    fun getLoanById(id: Long): LiveData<Loan>

    @Query("select * from loans where loan_month = :month and loan_year = :year")
    fun getLoansByMonth(month: Int, year: Int): LiveData<List<Loan>>

    @Query("delete from loans where _id = :id")
    suspend fun deleteLoanById(id: Long)

    @Query("delete from loans")
    suspend fun clear()
}