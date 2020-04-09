package com.example.shoppinglist.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.models.ShoppingListModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch

class ArchivedShoppingListsFragmentViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    val archivedShoppingListsListLiveData = MutableLiveData<List<ShoppingListModel>>()

    fun updateOrInsertList(newShoppingList: ShoppingListModel) {
        viewModelScope.launch {
            repository.updateOrInsert(newShoppingList)
            fetchArchivedLists()
        }
    }

    fun deleteList(shoppingListModel: ShoppingListModel){
        viewModelScope.launch {
            repository.remove(shoppingListModel)
            fetchArchivedLists()
        }
    }

    fun fetchArchivedLists() {
        viewModelScope.launch {
           archivedShoppingListsListLiveData.value = repository.getArchivedLists()
        }
    }
}