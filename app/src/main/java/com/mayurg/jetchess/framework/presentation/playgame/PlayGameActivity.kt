package com.mayurg.jetchess.framework.presentation.playgame

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardMainContainer
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Game
import com.mayurg.jetchess.framework.presentation.playgame.gameview.MoveResult
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme


class PlayGameActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PlayGameView()
            }
        }
    }


    @Composable
    fun PlayGameView() {
        var moveResult by remember { mutableStateOf<MoveResult>(MoveResult.Success(Game())) }
        var selection: PiecePosition? by remember { mutableStateOf(null) }

        when (val result = moveResult) {
            is MoveResult.Success -> {
                val game = result.game

                val onSelect: (PiecePosition) -> Unit = {
                    val sel = selection
                    if (game.canSelect(it)) {
                        selection = it
                    } else if (sel != null && game.canMove(sel, it)) {
                        moveResult = game.doMove(sel, it)
                        selection = null
                    }
                }

                Column {
                    Text(text = "Player 1", color = Color.White, modifier = Modifier.padding(16.dp))
                    BoardMainContainer(
                        game = game,
                        selection = selection,
                        moves = game.movesForPieceAt(selection),
                        didTap = onSelect
                    )
                    Text(text = "Player 2", color = Color.White, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }


}