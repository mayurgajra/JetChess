package com.mayurg.jetchess.framework.presentation.playgame

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import com.mayurg.jetchess.framework.presentation.base.BaseActivity

class PlayGameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Text(text = "Game")
            }
        }
    }
}