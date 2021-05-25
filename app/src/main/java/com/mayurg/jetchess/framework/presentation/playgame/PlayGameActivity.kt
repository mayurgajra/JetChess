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
        val numbers = (1..64).toList()

        LazyVerticalGrid(
            cells = GridCells.Fixed(8)
        ) {
            items(numbers.size) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(color = if (it % 2 == 0) darkSquare else lightSquare),
                ) {
                    Text(text = "A")
                }
            }
        }
    }

}