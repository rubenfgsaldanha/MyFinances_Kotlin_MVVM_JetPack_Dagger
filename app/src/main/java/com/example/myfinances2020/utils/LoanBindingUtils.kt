package com.example.myfinances2020.utils

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myfinances2020.repository.database.entities.Loan
import com.example.myfinances2020.repository.database.entities.formatDate

@BindingAdapter("loanee")
fun TextView.setLoanee(item: Loan?) = item?.let { text = item.thirdParty }

@BindingAdapter("loanAmount")
fun TextView.setLoanAmount(item: Loan?) = item?.let { text = item.amount.toString() }

@BindingAdapter("loanDate")
fun TextView.setLoanDate(item: Loan?) = item?.let { text = item.formatDate() }

@BindingAdapter("loanImage")
fun ImageView.setLoanImage(item: Loan?){
    item?.let {
        setColorFilter(when(item.isLender){
            true -> Color.RED
            false -> Color.GREEN
        })
    }
}