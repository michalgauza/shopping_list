package com.example.shoppinglist

import androidx.room.TypeConverter
import com.example.shoppinglist.models.ProductModel
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: MutableList<ProductModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): MutableList<ProductModel> =
        Gson().fromJson(value, Array<ProductModel>::class.java).toMutableList()
}