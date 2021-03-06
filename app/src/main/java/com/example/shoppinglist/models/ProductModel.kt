package com.example.shoppinglist.models

import java.util.*

data class ProductModel(
    val id: String = Date().time.toString(),
    val name: String,
    var isBought: Boolean = false
)