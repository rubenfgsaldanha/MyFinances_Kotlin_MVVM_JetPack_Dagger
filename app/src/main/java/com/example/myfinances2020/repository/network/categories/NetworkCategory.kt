package com.example.myfinances2020.repository.network.categories

import com.example.myfinances2020.repository.database.entities.Category

data class NetworkCategory(
    val _id: Long,
    val label: String,
    val color: Int
)

fun List<NetworkCategory>.asDatabaseModel() =
    this.map { category ->
        Category(
            _id = category._id,
            label = category.label,
            color = category.color
        )
    }.toTypedArray()