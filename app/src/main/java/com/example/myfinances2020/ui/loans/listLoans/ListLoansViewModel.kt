package com.example.myfinances2020.ui.loans.listLoans

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.TransactionsRepository
import javax.inject.Inject

class ListLoansViewModel @Inject constructor(private val transactionsRepository: TransactionsRepository) : ViewModel()