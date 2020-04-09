package com.example.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.databinding.ListCardViewBinding
import com.example.shoppinglist.models.ShoppingListModel
import com.example.shoppinglist.utils.shoppingListDiffUtilCallback

class ShoppingListsRecyclerViewAdapter :
    ListAdapter<ShoppingListModel, ShoppingListsRecyclerViewAdapter.ShoppingListsViewHolder>(
        shoppingListDiffUtilCallback
    ) {

    var removeShoppingListCallback: ((ShoppingListModel) -> Unit)? = null
    var archiveShoppingListCallback: ((ShoppingListModel, Boolean) -> Unit)? = null
    var shoppingListDetailsCallback: ((ShoppingListModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListsViewHolder {
        val binding =
            ListCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingListsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShoppingListsViewHolder(private val binding: ListCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ShoppingListModel
        ) {
            with(binding) {
                shoppingList = item
                listCardViewConstraintLayout.setOnClickListener {
                    shoppingListDetailsCallback?.invoke(item)
                }
                listCardViewDeleteButton.setOnClickListener {
                    removeShoppingListCallback?.invoke(item)
                }
                listCardViewCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    archiveShoppingListCallback?.invoke(item, isChecked)
                }
            }
        }
    }
}