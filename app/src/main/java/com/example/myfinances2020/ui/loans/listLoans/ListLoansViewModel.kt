package com.example.myfinances2020.ui.loans.listLoans

import androidx.lifecycle.ViewModel
import com.example.myfinances2020.repository.LoansRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class ListLoansViewModel @Inject constructor(private val loansRepository: LoansRepository) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onLoanClicked(id: Long) {
        //
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}