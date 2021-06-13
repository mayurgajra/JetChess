package com.mayurg.jetchess.framework.presentation.playgame

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardMainContainer
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Game
import com.mayurg.jetchess.framework.presentation.playgame.gameview.MoveResult
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme

@ExperimentalFoundationApi
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
                    BoardMainContainer(
                        game = game,
                        selection = selection,
                        moves = game.movesForPieceAt(selection),
                        didTap = onSelect
                    )
                }
            }
        }
    }


}