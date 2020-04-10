package com.example.shoppinglist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoppinglist.db.ShoppingListDatabase
import com.example.shoppinglist.db.ShoppingListRepository
import com.example.shoppinglist.models.ShoppingListModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    lateinit var testDatabase: ShoppingListDatabase
    lateinit var repo: ShoppingListRepository

    @Before
    fun setupDatabase() {
        testDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            ShoppingListDatabase::class.java
        ).build()
        repo = ShoppingListRepository(testDatabase.shoppingListDao())
    }

    @Test
    fun addEmptyListToDatabase() = runBlocking {
        val testShoppingList =
            ShoppingListModel(name = "testList", shoppingItemsList = mutableListOf())
        repo.updateOrInsertShoppingList(testShoppingList)
        assertTrue(repo.getCurrentShoppingLists().first() == testShoppingList)
        assertTrue(repo.getCurrentShoppingLists().size == 1)
    }

    @Test
    fun changeListStatus() = runBlocking {
        val testShoppingList =
            ShoppingListModel(name = "list to archive", shoppingItemsList = mutableListOf())
        repo.updateOrInsertShoppingList(testShoppingList)
        assertTrue(repo.getCurrentShoppingLists().first() == testShoppingList)
        testShoppingList.isArchived = true
        repo.updateOrInsertShoppingList(testShoppingList)
        assertTrue(repo.getCurrentShoppingLists().firstOrNull() == null)
        assertTrue(repo.getArchivedShoppingLists().first().isArchived)
    }

    @Test
    fun getShoppingListById() = runBlocking {
        val firstListId = Date().time.toString()
        val firstShoppingList = ShoppingListModel(
            id = firstListId,
            name = "first shopping list",
            shoppingItemsList = mutableListOf()
        )
        repo.updateOrInsertShoppingList(firstShoppingList)
        val secondShoppingList =
            ShoppingListModel(name = "second shopping list", shoppingItemsList = mutableListOf())
        repo.updateOrInsertShoppingList(secondShoppingList)
        assertTrue(repo.getShoppingListById(firstListId) == firstShoppingList)
    }

    @Test
    fun deleteShoppingList() = runBlocking {
        val shoppingList =
            ShoppingListModel(name = "first shopping list", shoppingItemsList = mutableListOf())
        repo.updateOrInsertShoppingList(shoppingList)
        repo.removeShoppingList(shoppingList)
        assertTrue(repo.getCurrentShoppingLists().isEmpty())
    }
}