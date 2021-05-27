package com.mayurg.jetchess.framework.presentation.playgame

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.framework.presentation.base.BaseActivity

@ExperimentalFoundationApi
class PlayGameActivity : BaseActivity() {
    val darkSquare = Color(0xFF779556)
    val lightSquare = Color(0xFFEBECD0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Board()
            }
        }
    }

    @Composable
    fun Board() {
        Column {
            for (i in 0 until 8) {
                Row {
                    for (j in 0 until 8) {
                        val isLightSquare = i % 2 == j % 2
                        val squareColor = if (isLightSquare) lightSquare else darkSquare
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(squareColor)
                        ) {
                            Text(text = "${i + j}")
                        }
                    }
                }
            }
        }
    }

}