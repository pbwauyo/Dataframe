package com.example.dataframe.utils

import android.content.Context
import android.widget.Toast

class CustomToast constructor(private val context: Context) {
    fun showLongToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}