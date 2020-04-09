package com.example.shoppinglist.utils

import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)

fun Date.toSimpleDateFormat(): String = simpleDateFormat.format(this)

fun Long.toSimpleDateFormat(): String = Date(this).toSimpleDateFormat()