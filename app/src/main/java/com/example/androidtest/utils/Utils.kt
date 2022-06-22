package com.example.androidtest.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Utils {
    fun showSnackBar(view: View, content: String) {
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show()
    }
}