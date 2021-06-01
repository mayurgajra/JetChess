package com.mayurg.jetchess.framework.presentation.playgame.pieceview

import com.mayurg.jetchess.framework.presentation.utils.customexceptions.InvalidPieceIdException

private fun getPieceTypesFromId(id: String): Pair<PieceType, PieceColor> {
    val chars = id.toCharArray()
    if (chars.size != 3) throw InvalidPieceIdException("Piece id must be of exact 3 characters")

    return Pair(PieceType.Bishop,PieceColor.Black)
}