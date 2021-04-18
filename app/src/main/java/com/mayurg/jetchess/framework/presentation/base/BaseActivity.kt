package com.mayurg.jetchess.framework.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mayurg.jetchess.R

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }
}