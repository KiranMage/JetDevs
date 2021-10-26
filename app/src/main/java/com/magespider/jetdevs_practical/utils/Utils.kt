package com.magespider.jetdevs_practical.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {
    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}