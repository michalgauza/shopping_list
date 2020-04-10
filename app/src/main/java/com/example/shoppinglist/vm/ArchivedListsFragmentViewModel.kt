package com.example.shoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.models.ShoppingListModel

import kotlinx.coroutines.launch

class ArchivedListsFragmentViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    private val _archivedShoppingListsListLiveData = MutableLiveData<List<ShoppingListModel>>()
    val archivedShoppingListsListLiveData: LiveData<List<ShoppingListModel>> = _archivedShoppingListsListLiveData
    fun updateOrInsertList(newShoppingList: ShoppingListModel) {
        viewModelScope.launch {
            repository.updateOrInsertShoppingList(newShoppingList)
            fetchArchivedLists()
        }
    }

    fun deleteList(shoppingListModel: ShoppingListModel){
        viewModelScope.launch {
            repository.removeShoppingList(shoppingListModel)
            fetchArchivedLists()
        }
    }

    fun fetchArchivedLists() {
        viewModelScope.launch {
           _archivedShoppingListsListLiveData.value = repository.getArchivedShoppingLists()
        }
    }
}