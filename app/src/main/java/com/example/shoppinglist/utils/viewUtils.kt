package com.example.shoppinglist.utils

import android.view.View

fun View.setViewVisibility(isVisible: Boolean) =
    if (isVisible) this.visibility = View.VISIBLE else this.visibility = View.INVISIBLE