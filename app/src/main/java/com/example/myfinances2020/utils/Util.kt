package com.example.myfinances2020.utils

import java.util.*

fun formatDateWithoutDay(month: Int, year: Int): String = "${month+1}/$year"

fun formatBtnDate(day: Int, month: Int, year: Int): String = "$day/${month+1}/$year"

fun splitDate(dateString: String): List<String> = dateString.split("/")

fun getCurrentDate(): Calendar = Calendar.getInstance()

fun setCurrentDate() : String{
    val c = getCurrentDate()
    return formatDateWithoutDay(c.get(Calendar.MONTH), c.get(Calendar.YEAR))
}