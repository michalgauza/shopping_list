package com.example.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.models.ShoppingListModel

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateShoppingList(shoppingListModel: ShoppingListModel)

    @Query(value = "select * from shopping_lists where id = :id")
    suspend fun getShoppingListById(id: String): ShoppingListModel

    @Query(value = "select * from shopping_lists where isArchived == 0 ORDER BY date")
    suspend fun getCurrentShoppingLists(): List<ShoppingListModel>

    @Query(value = "select * from shopping_lists where isArchived == 1 ORDER BY date")
    suspend fun getArchivedShoppingLists(): List<ShoppingListModel>

    @Delete
    suspend fun deleteShoppingList(shoppingListModel: ShoppingListModel)
}