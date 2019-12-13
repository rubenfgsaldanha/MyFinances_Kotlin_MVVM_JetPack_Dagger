package com.example.myfinances2020.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myfinances2020.repository.database.entities.Loan

@BindingAdapter("loanee")
fun TextView.setLoanee(item: Loan?) = item?.let { text = item.loanee }

@BindingAdapter("loanAmount")
fun TextView.setLoanAmount(item: Loan?) = item?.let { text = item.amount.toString() }