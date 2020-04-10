package com.example.shoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.models.ShoppingListModel

import kotlinx.coroutines.launch

class CurrentListsFragmentViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    private val _currentShoppingListsListLiveData = MutableLiveData<List<ShoppingListModel>>()
    val currentShoppingListsListLiveData: LiveData<List<ShoppingListModel>> =
        _currentShoppingListsListLiveData

    fun updateOrInsertList(newShoppingList: ShoppingListModel) {
        viewModelScope.launch {
            repository.updateOrInsertShoppingList(newShoppingList)
            fetchCurrentLists()
        }
    }

    fun deleteList(shoppingListModel: ShoppingListModel) {
        viewModelScope.launch {
            repository.removeShoppingList(shoppingListModel)
            fetchCurrentLists()
        }
    }

    fun fetchCurrentLists() {
        viewModelScope.launch {
            _currentShoppingListsListLiveData.value = repository.getCurrentShoppingLists()
        }
    }
}