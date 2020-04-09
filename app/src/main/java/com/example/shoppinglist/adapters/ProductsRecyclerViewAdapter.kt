package com.example.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.databinding.ItemCardViewBinding
import com.example.shoppinglist.models.ProductModel
import com.example.shoppinglist.utils.setViewVisibility
import com.example.shoppinglist.utils.shoppingItemsListDiffUtilCallback

class ProductsRecyclerViewAdapter(private val isEditable: Boolean) :
    ListAdapter<ProductModel, ProductsRecyclerViewAdapter.ShoppingItemsViewHolder>(
        shoppingItemsListDiffUtilCallback
    ) {

    var removeProductCallback: ((ProductModel) -> Unit)? = null
    var buyProductCallback: ((ProductModel, Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemsViewHolder {
        val binding =
            ItemCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingItemsViewHolder(binding, isEditable)
    }

    override fun onBindViewHolder(holder: ShoppingItemsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShoppingItemsViewHolder(
        private val binding: ItemCardViewBinding,
        private val isEditable: Boolean
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            with(binding) {
                itemCardViewConstraintLayout.background
                product = item
                itemCardViewCheckBox.apply {
                    setViewVisibility(isEditable)
                    isChecked = item.isBought
                    setOnCheckedChangeListener { _, isChecked ->
                        buyProductCallback?.invoke(item, isChecked)
                    }
                }
                itemCardViewDeleteButton.apply {
                    setViewVisibility(isEditable)
                    setOnClickListener {
                        removeProductCallback?.invoke(item)
                    }
                }
            }
        }

    }
}