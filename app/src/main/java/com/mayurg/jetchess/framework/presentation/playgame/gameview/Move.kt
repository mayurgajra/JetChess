package com.mayurg.jetchess.framework.presentation.playgame.gameview

import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition
import java.io.Serializable

data class Move(val fromPosition: PiecePosition, val toPosition: PiecePosition): Serializable {
    fun contains(position: PiecePosition): Boolean {
        return fromPosition == position || toPosition == position
    }
}
