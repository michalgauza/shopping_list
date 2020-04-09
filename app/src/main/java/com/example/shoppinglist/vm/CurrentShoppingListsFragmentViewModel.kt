package com.example.shoppinglist.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.models.ShoppingListModel

import kotlinx.coroutines.launch

class CurrentShoppingListsFragmentViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    val currentShoppingListsListLiveData = MutableLiveData<List<ShoppingListModel>>()

    fun updateOrInsertList(newShoppingList: ShoppingListModel) {
        viewModelScope.launch {
            repository.updateOrInsert(newShoppingList)
            fetchCurrentLists()
        }
    }

    fun deleteList(shoppingListModel: ShoppingListModel){
        viewModelScope.launch {
            repository.remove(shoppingListModel)
            fetchCurrentLists()
        }
    }

    fun fetchCurrentLists() {
        viewModelScope.launch {
            currentShoppingListsListLiveData.value = repository.getCurrentLists()
        }
    }
}