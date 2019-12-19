package com.example.myfinances2020.utils

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myfinances2020.repository.database.entities.Transaction
import com.example.myfinances2020.repository.database.entities.formatDate

@BindingAdapter("transactionDate")
fun TextView.setDate(item: Transaction?) = item?.let { text = item.formatDate() }

@BindingAdapter("transactionImage")
fun ImageView.setTransactionImage(item: Transaction?) {
    item?.let {
        setColorFilter(when (item.isExpense) {
            true -> Color.RED
            false -> Color.GREEN
        })
    }
}

@BindingAdapter("transactionCategoryComment")
fun TextView.setCategoryOrComment(item: Transaction?) {
    item?.let {
        text = if (item.comment != null && item.comment.isNotEmpty()) item.comment else item.category
    }
}

@BindingAdapter("transactionAmount")
fun TextView.setAmount(item: Transaction?) = item?.let { text = item.amount.toString() }

@BindingAdapter("transactionComment")
fun TextView.setComment(item: Transaction?) = item?.let { text = item.comment }