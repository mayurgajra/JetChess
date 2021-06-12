package com.mayurg.jetchess.framework.presentation.playgame.pieceview

sealed class PieceColor {
    object White : PieceColor()
    object Black : PieceColor()

    fun other(): PieceColor {
        return if (this == White) Black else White
    }
}
