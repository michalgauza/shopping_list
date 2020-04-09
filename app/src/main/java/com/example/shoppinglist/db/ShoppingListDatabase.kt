package com.example.shoppinglist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppinglist.Converters
import com.example.shoppinglist.models.ShoppingListModel

@Database(entities = [ShoppingListModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
}