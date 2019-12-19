package com.example.myfinances2020.repository

import com.example.myfinances2020.repository.database.daos.LoanDao
import com.example.myfinances2020.repository.database.entities.Loan
import com.example.myfinances2020.repository.network.Result
import com.example.myfinances2020.repository.network.loans.LoanDataSource
import com.example.myfinances2020.repository.network.loans.asDatabaseModel
import com.example.myfinances2020.utils.getCurrentDate
import java.util.*
import javax.inject.Inject

class LoansRepository @Inject constructor(private val loanDao: LoanDao, private val loanDataSource: LoanDataSource?) {

    private val date = getCurrentDate()
    var loans = loanDao.getLoansByMonth(date.get(Calendar.MONTH) + 1, date.get(Calendar.YEAR))

    suspend fun insertLoan(l: Loan) = loanDao.insert(l)

    suspend fun updateLoan(l: Loan) = loanDao.update(l)

    suspend fun deleteLoanById(id: Long) = loanDao.deleteLoanById(id)

    fun getLoanById(id: Long) = loanDao.getLoanById(id)

    fun getCurrentMonthLoans(month: Int, year: Int) = loanDao.getLoansByMonth(month, year)

    suspend fun refreshLoans() {
        loanDataSource?.let {
            val result = loanDataSource.getLoans()
            if (result is Result.Success) {
                val networkLoanList = result.data
                loanDao.insertAll(*networkLoanList.asDatabaseModel())
            }
        }
    }
}