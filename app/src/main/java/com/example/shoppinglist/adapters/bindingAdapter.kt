package com.example.shoppinglist.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.shoppinglist.utils.toSimpleDateFormat

@BindingAdapter("dateToString")
fun TextView.dateToString(dateInLong: Long) {
    text = dateInLong.toSimpleDateFormat()
}