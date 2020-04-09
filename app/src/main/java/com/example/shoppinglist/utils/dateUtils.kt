package com.example.shoppinglist.utils

import java.text.SimpleDateFormat
import java.util.*

const val SDF_PATTERN = "yyyy-MM-dd HH:mm"
val simpleDateFormat = SimpleDateFormat(SDF_PATTERN, Locale.ENGLISH)

fun Date.toSimpleDateFormat(): String = simpleDateFormat.format(this)

fun Long.toSimpleDateFormat(): String = Date(this).toSimpleDateFormat()