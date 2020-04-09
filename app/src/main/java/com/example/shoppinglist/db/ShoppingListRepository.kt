package com.example.shoppinglist.db

import com.example.shoppinglist.models.ShoppingListModel

class ShoppingListRepository(private val shoppingListDao: ShoppingListDao) {

    suspend fun getCurrentLists() = shoppingListDao.getCurrentShoppingLists()

    suspend fun getArchivedLists() = shoppingListDao.getArchivedShoppingLists()

    suspend fun updateOrInsert(newShoppingList: ShoppingListModel) {
        shoppingListDao.insertOrUpdateList(newShoppingList)
    }

    suspend fun remove(item: ShoppingListModel) {
        shoppingListDao.deleteList(item)
    }

    suspend fun getListById(id: String) = shoppingListDao.getShoppingListById(id)
}