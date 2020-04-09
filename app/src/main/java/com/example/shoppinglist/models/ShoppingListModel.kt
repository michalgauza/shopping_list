package com.example.shoppinglist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shopping_lists")
data class ShoppingListModel(
    @PrimaryKey(autoGenerate = false)
    val id: String = Date().time.toString(),
    var name: String,
    val shoppingItemsList: MutableList<ProductModel>,
    var isArchived: Boolean = false,
    val date: Long = Date().time
)