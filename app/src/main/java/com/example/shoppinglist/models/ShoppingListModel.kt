package com.example.shoppinglist.models

data class ShoppingListModel(
    var name: String,
    val shoppingItemsList: MutableList<ShoppingItemModel>,
    var isArchived: Boolean = false
)