package com.cartrack.challenge.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class Utils {
    companion object {
        fun hideKeyboard(activity: Activity) {
            // Check if no view has focus:
            val view = activity.currentFocus
            val inputManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            view?.let {
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
}