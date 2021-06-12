package com.mayurg.jetchess.framework.presentation.playgame.gameview

import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition

data class Move(val fromPosition: PiecePosition, val toPosition: PiecePosition) {
    fun contains(position: PiecePosition): Boolean {
        return fromPosition == position || toPosition == position
    }
}
