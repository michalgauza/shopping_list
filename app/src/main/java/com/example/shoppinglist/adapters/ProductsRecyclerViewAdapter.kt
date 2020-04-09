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
    ListAdapter<ProductModel, ProductsRecyclerViewAdapter.ProductsViewHolder>(
        shoppingItemsListDiffUtilCallback
    ) {

    var removeProductCallback: ((ProductModel) -> Unit)? = null
    var buyProductCallback: ((ProductModel, Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ItemCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductsViewHolder(
        private val binding: ItemCardViewBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductModel) {
            with(binding) {
                this.product = product
                setupCheckBox(product)
                setupDeleteButton(product)
            }
        }

        private fun ItemCardViewBinding.setupDeleteButton(product: ProductModel) {
            productCardViewDeleteButton.apply {
                setViewVisibility(isEditable)
                setOnClickListener {
                    removeProductCallback?.invoke(product)
                }
            }
        }

        private fun ItemCardViewBinding.setupCheckBox(product: ProductModel) {
            productCardViewCheckBox.apply {
                setViewVisibility(isEditable)
                isChecked = product.isBought
                setOnCheckedChangeListener { _, isChecked ->
                    buyProductCallback?.invoke(product, isChecked)
                }
            }
        }
    }
}