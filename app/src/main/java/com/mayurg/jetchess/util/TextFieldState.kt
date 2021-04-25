package com.mayurg.jetchess.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TextFieldState {
    var text: String by mutableStateOf("")
}