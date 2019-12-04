package com.example.myfinances2020.data.database.repository

import com.example.myfinances2020.data.database.MyFinancesDatabase
import com.example.myfinances2020.data.database.entities.Transaction
import com.example.myfinances2020.data.network.Result
import com.example.myfinances2020.data.network.TransactionDataSource
import com.example.myfinances2020.data.network.asDatabaseModel
import com.example.myfinances2020.utils.getCurrentDate
import java.util.*

class TransactionsRepository(val database: MyFinancesDatabase){

    private val date = getCurrentDate()
    private val transactionDao = database.transactionDao
    var transactions = transactionDao.getTransactionsByMonth(date.get(Calendar.MONTH)+1, date.get(Calendar.YEAR))

    suspend fun insertTransaction(t: Transaction) = transactionDao.insert(t)

    suspend fun updateTransaction(t: Transaction) = transactionDao.update(t)

    suspend fun deleteTransactionById(id: Long) = transactionDao.deleteTransactionById(id)

    fun getCurrentMonthTransactions(month: Int, year: Int){ transactions = transactionDao.getTransactionsByMonth(month, year) }

    suspend fun refreshTransactions(){
        val result = TransactionDataSource().getTransactions()
        if(result is Result.Success){
            val networkTransactionList = result.data
            database.transactionDao.insertAll(*networkTransactionList.asDatabaseModel())
        }
    }
}