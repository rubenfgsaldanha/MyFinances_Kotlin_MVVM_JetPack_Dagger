package com.example.myfinances2020.utils

import com.example.myfinances2020.data.database.entities.Transaction
import java.util.*

fun formatDate(item: Transaction): String = "${item.day}/${item.month}/${item.year}"

fun formatDateWithoutDay(month: Int, year: Int): String = "${month+1}/$year"

fun formatBtnDate(day: Int, month: Int, year: Int): String = "$day/${month+1}/$year"

fun splitDate(dateString: String): List<String> = dateString.split("/")

fun getCurrentDate(): Calendar = Calendar.getInstance()