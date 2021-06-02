package com.mayurg.jetchess.framework.presentation.playgame.pieceview

import androidx.annotation.DrawableRes
import com.mayurg.jetchess.R

data class Piece(
    val id: String,
    val type: PieceType,
    val pieceColor: PieceColor
) {
    companion object {
        fun pieceFromString(id: String): Piece {
            val type = getPieceTypesFromId(id)
            return Piece(id, type.first, type.second)
        }
    }

    @DrawableRes
    fun getPieceDrawable(): Int {
        return when (type) {
            PieceType.Pawn -> if (pieceColor is PieceColor.White) R.drawable.ic_vector_white_pawn else R.drawable.ic_vector_black_pawn
            PieceType.Knight -> if (pieceColor is PieceColor.White) R.drawable.ic_vector_white_knight else R.drawable.ic_vector_black_knight
            PieceType.Bishop -> if (pieceColor is PieceColor.White) R.drawable.ic_vector_white_bishop else R.drawable.ic_vector_black_bishop
            PieceType.King -> if (pieceColor is PieceColor.White) R.drawable.ic_vector_white_king else R.drawable.ic_vector_black_king
            PieceType.Queen -> if (pieceColor is PieceColor.White) R.drawable.ic_vector_white_queen else R.drawable.ic_vector_black_queen
            PieceType.Rook -> if (pieceColor is PieceColor.White) R.drawable.ic_vector_white_rook else R.drawable.ic_vector_black_rook
        }
    }
}
