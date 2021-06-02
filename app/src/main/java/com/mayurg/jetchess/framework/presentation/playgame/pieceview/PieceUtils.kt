package com.mayurg.jetchess.framework.presentation.playgame.pieceview

import com.mayurg.jetchess.framework.presentation.utils.customexceptions.InvalidPieceIdException

fun getPieceTypesFromId(id: String): Pair<PieceType, PieceColor> {
    val chars = id.toCharArray()
    if (chars.size != 3) throw InvalidPieceIdException("Piece id must be of exact 3 characters")

    val pieceColor = when (chars[0]) {
        'W' -> PieceColor.White
        'B' -> PieceColor.Black
        else -> throw InvalidPieceIdException("First letter of piece id must be W or B")
    }

    val pieceType = when (chars[1]) {
        'P' -> PieceType.Pawn
        'R' -> PieceType.Rook
        'Q' -> PieceType.Queen
        'K' -> PieceType.King
        'N' -> PieceType.Knight
        'B' -> PieceType.Bishop
        else -> throw InvalidPieceIdException(
            "Second letter of piece id must be one of:" +
                    "'P','R', 'Q','K','N','B'"
        )
    }
    return Pair(pieceType, pieceColor)
}