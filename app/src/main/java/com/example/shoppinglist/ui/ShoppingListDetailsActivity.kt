package com.example.shoppinglist.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.adapters.ProductsRecyclerViewAdapter
import com.example.shoppinglist.databinding.ActivityDetailsBinding
import com.example.shoppinglist.models.ProductModel
import com.example.shoppinglist.models.ShoppingListModel
import com.example.shoppinglist.utils.closeKeyboard
import com.example.shoppinglist.utils.setViewVisibility
import com.example.shoppinglist.utils.showKeyboard
import com.example.shoppinglist.vm.ShoppingListDetailsActivityViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


class ShoppingListDetailsActivity : AppCompatActivity() {

    private val viewModel by inject<ShoppingListDetailsActivityViewModel>()

    private val shoppingListLiveDataObserver = Observer<ShoppingListModel> { shoppingListModel ->
        shoppingListsRecyclerViewAdapter.submitList(shoppingListModel.shoppingItemsList.sortedBy { it.isBought })
    }

    private var shoppingListName: String? = null
    private var shoppingListId: String? = null
    private var shoppingListIsArchived: Boolean? = null

    lateinit var binding: ActivityDetailsBinding

    lateinit var shoppingListsRecyclerViewAdapter: ProductsRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        intent.extras?.run {
            shoppingListName = getString(SHOPPING_LIST_NAME_KEY)
            shoppingListId = getString(SHOPPING_LIST_ID_KEY)
            shoppingListIsArchived = getBoolean(SHOPPING_LIST_IS_ARCHIVED_KEY)

        }
        with(binding) {
            viewModel = this@ShoppingListDetailsActivity.viewModel
            lifecycleOwner = this@ShoppingListDetailsActivity
            detailsActivityToolbar.title = shoppingListName
            shoppingListIsArchived?.let {
                this.detailsActivityFab.apply {
                    setViewVisibility(it.not())
                    setOnClickListener {
                        setupDialog()
                    }
                }
            }
        }
        setupRecyclerAdapter()
        with(viewModel) {
            shoppingListLiveData.observe(
                this@ShoppingListDetailsActivity,
                shoppingListLiveDataObserver
            )
            shoppingListId?.let { fetchShoppingList(it) }
        }
    }

    private fun setupRecyclerAdapter() {
        shoppingListIsArchived?.let {
            shoppingListsRecyclerViewAdapter =
                ProductsRecyclerViewAdapter(it.not())
        }

        with(binding.detailsActivityRecyclerView) {
            layoutManager = LinearLayoutManager(this@ShoppingListDetailsActivity)
            adapter = shoppingListsRecyclerViewAdapter
        }

        with(shoppingListsRecyclerViewAdapter) {
            buyProductCallback = { product, isBought ->
                product.isBought = isBought
                viewModel.updateShoppingList(product)
            }
            removeProductCallback = { product ->
                viewModel.deleteProduct(product)
            }
        }
    }

    private fun setupDialog() {
        val input = EditText(this).apply {
            requestFocus()
        }
        showKeyboard(this)
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.add_product_dialog_title))
            setView(input)
            setCancelable(false)
            setPositiveButton(getString(R.string.add_product_dialog_positive_button)) { _, _ ->
                addProduct(input)
            }
            setNegativeButton(getString(R.string.add_product_dialog_negative_button)) { dialog, _ -> dialog.cancel() }
            show()
        }
    }

    private fun addProduct(input: EditText) {
        if (input.text.isEmpty().not()) {
            viewModel.addNewProduct(ProductModel(name = input.text.toString()))
            closeKeyboard(this@ShoppingListDetailsActivity)
        } else {
            Snackbar.make(
                binding.detailsActivityConstraintLayout,
                getString(R.string.add_product_error_message),
                Snackbar.LENGTH_SHORT
            )
            closeKeyboard(this@ShoppingListDetailsActivity)
        }
    }

    companion object {
        const val SHOPPING_LIST_ID_KEY = "itemIdKey"
        const val SHOPPING_LIST_NAME_KEY = "itemNameKey"
        const val SHOPPING_LIST_IS_ARCHIVED_KEY = "itemIsArchivedKey"
        fun getIntent(
            context: Context,
            shoppingListName: String,
            shoppingListId: String,
            isArchived: Boolean
        ) =
            Intent(context, ShoppingListDetailsActivity::class.java).apply {
                putExtra(SHOPPING_LIST_NAME_KEY, shoppingListName)
                putExtra(SHOPPING_LIST_ID_KEY, shoppingListId)
                putExtra(SHOPPING_LIST_IS_ARCHIVED_KEY, isArchived)
            }
    }

}
