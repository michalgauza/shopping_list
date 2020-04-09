package com.example.shoppinglist.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.models.ProductModel
import com.example.shoppinglist.models.ShoppingListModel
import kotlinx.coroutines.launch

class DetailsActivityViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    val shoppingListLiveData = MutableLiveData<ShoppingListModel>()

    private fun updateOrInsertList(newShoppingList: ShoppingListModel) {
        viewModelScope.launch {
            repository.updateOrInsert(newShoppingList)
            fetchShoppingList(newShoppingList.id)
        }
    }

    fun fetchShoppingList(id: String) {
        viewModelScope.launch {
            shoppingListLiveData.value = repository.getListById(id)
        }
    }

    fun addNewProduct(newProduct: ProductModel) {
        val newShoppingList = shoppingListLiveData.value
        newShoppingList?.let {
            it.shoppingItemsList.add(0, newProduct)
            updateOrInsertList(it)
        }
    }

    fun updateShoppingList(updatedProduct: ProductModel) {
        shoppingListLiveData.value?.let {
            it.shoppingItemsList.firstOrNull { product -> product.id == updatedProduct.id }
                ?.isBought =
                updatedProduct.isBought
            updateOrInsertList(it)
        }
    }

    fun deleteProduct(product: ProductModel) {
        shoppingListLiveData.value?.let {
            it.shoppingItemsList.remove(product)
            updateOrInsertList(it)
        }
    }
}