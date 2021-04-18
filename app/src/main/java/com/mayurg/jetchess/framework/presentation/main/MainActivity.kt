package com.mayurg.jetchess.framework.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.mayurg.jetchess.framework.presentation.base.BaseActivity


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text(text = "Hello Jet Chess")
        }

    }
}