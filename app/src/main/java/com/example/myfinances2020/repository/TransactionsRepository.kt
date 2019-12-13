package com.example.myfinances2020.repository

import com.example.myfinances2020.repository.database.daos.TransactionDao
import com.example.myfinances2020.repository.database.entities.Transaction
import com.example.myfinances2020.repository.network.Result
import com.example.myfinances2020.repository.network.transactions.TransactionDataSource
import com.example.myfinances2020.repository.network.transactions.asDatabaseModel
import com.example.myfinances2020.utils.getCurrentDate
import java.util.*
import javax.inject.Inject

class TransactionsRepository @Inject constructor(private val transactionDao: TransactionDao,
                                                 private val transactionDataSource: TransactionDataSource?){

    private val date = getCurrentDate()
    var transactions = transactionDao.getTransactionsByMonth(date.get(Calendar.MONTH)+1, date.get(Calendar.YEAR))

    suspend fun insertTransaction(t: Transaction) = transactionDao.insert(t)

    suspend fun updateTransaction(t: Transaction) = transactionDao.update(t)

    suspend fun deleteTransactionById(id: Long) = transactionDao.deleteTransactionById(id)

    fun getCurrentMonthTransactions(month: Int, year: Int){ transactions = transactionDao.getTransactionsByMonth(month, year) }

    suspend fun refreshTransactions(){
        transactionDataSource?.let {
            val result = transactionDataSource.getTransactions()
            if(result is Result.Success){
                val networkTransactionList = result.data
                transactionDao.clear()
                transactionDao.insertAll(*networkTransactionList.asDatabaseModel())
            }
        }
    }
}