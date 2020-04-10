package com.example.shoppinglist.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.models.ProductModel
import com.example.shoppinglist.models.ShoppingListModel

val shoppingListDiffUtilCallback = object : DiffUtil.ItemCallback<ShoppingListModel>() {

    override fun areItemsTheSame(oldItem: ShoppingListModel, newItem: ShoppingListModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ShoppingListModel,
        newItem: ShoppingListModel
    ): Boolean = (oldItem.isArchived == newItem.isArchived && oldItem.name == newItem.name && oldItem.shoppingItemsList == newItem.shoppingItemsList)
}

val shoppingItemsListDiffUtilCallback = object : DiffUtil.ItemCallback<ProductModel>() {

    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
        oldItem.isBought == newItem.isBought
}