package com.mayurg.jetchess.framework.presentation.playgame.pieceview

sealed class PieceType(val points: Int) {
    object Pawn : PieceType(1)
    object Knight : PieceType(3)
    object Bishop : PieceType(3)
    object Rook : PieceType(5)
    object Queen : PieceType(8)
    object King : PieceType(Int.MAX_VALUE)
}
