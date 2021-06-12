package com.mayurg.jetchess.framework.presentation.playgame.gameview

import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PieceType

sealed class MoveResult {
    data class Success(val game: Game) : MoveResult()
    data class Promotion(val onPieceSelection: (PieceType) -> MoveResult) : MoveResult()
}
