package com.example.shoppinglist.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.models.ProductModel
import com.example.shoppinglist.models.ShoppingListModel

val shoppingListDiffUtilCallback = object : DiffUtil.ItemCallback<ShoppingListModel>() {

    override fun areItemsTheSame(oldItem: ShoppingListModel, newItem: ShoppingListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShoppingListModel, newItem: ShoppingListModel): Boolean {
        return (oldItem.isArchived == newItem.isArchived && oldItem.name == newItem.name)
    }
}

val shoppingItemsListDiffUtilCallback = object : DiffUtil.ItemCallback<ProductModel>() {

    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.isBought == newItem.isBought
    }
}