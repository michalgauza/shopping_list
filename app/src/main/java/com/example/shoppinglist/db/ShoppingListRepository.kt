package com.example.shoppinglist.db

import com.example.shoppinglist.models.ShoppingListModel

class ShoppingListRepository(private val shoppingListDao: ShoppingListDao) {

    suspend fun getCurrentShoppingLists() = shoppingListDao.getCurrentShoppingLists()

    suspend fun getArchivedShoppingLists() = shoppingListDao.getArchivedShoppingLists()

    suspend fun updateOrInsertShoppingList(newShoppingList: ShoppingListModel) {
        shoppingListDao.insertOrUpdateShoppingList(newShoppingList)
    }

    suspend fun removeShoppingList(shoppingList: ShoppingListModel) {
        shoppingListDao.deleteShoppingList(shoppingList)
    }

    suspend fun getShoppingListById(id: String) = shoppingListDao.getShoppingListById(id)
}