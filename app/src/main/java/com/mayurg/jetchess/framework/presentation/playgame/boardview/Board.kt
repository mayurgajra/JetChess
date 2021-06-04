package com.mayurg.jetchess.framework.presentation.playgame.boardview

import com.mayurg.jetchess.framework.presentation.playgame.pieceview.Piece
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition

data class Board(val pieces: List<List<Piece?>> = INITIAL_BOARD) {
    companion object {
        private val ALL_POSITIONS = (0 until 8).flatMap { y ->
            (0 until 8).map { x -> PiecePosition(x, y) }
        }
    }

    val allPositions = ALL_POSITIONS
    val allPieces: List<Pair<PiecePosition, Piece>> = allPositions
        .mapNotNull { position ->
            pieces[position.y][position.x]?.let {
                position to it
            }
        }
}