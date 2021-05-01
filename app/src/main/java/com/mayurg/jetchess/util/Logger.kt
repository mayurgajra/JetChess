package com.mayurg.jetchess.util

import android.util.Log
import com.mayurg.jetchess.util.Constants.DEBUG
import com.mayurg.jetchess.util.Constants.TAG

var isUnitTest = false

fun printLogD(className: String?, message: String) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    } else if (DEBUG && isUnitTest) {
        println("$className: $message")
    }
}

