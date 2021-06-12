package com.mayurg.jetchess.framework.presentation.playgame.boardview

import com.mayurg.jetchess.framework.presentation.playgame.pieceview.Piece
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PieceDelta
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PieceType

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

    fun pieceAt(position: PiecePosition): Piece? {
        return pieces.getOrNull(position.y)?.getOrNull(position.x)
    }

    fun movePiece(from: PiecePosition, to: PiecePosition): Board {
        val piece = pieceAt(from)
        val newPieces = pieces.map { it.toMutableList() }.toMutableList()

        newPieces[to.y][to.x] = piece
        newPieces[from.y][from.y] = null

        return Board(newPieces.map { it.toList() }.toList())
    }

    fun firstPositionOfPiece(where: (Piece) -> Boolean): PiecePosition? {
        return allPieces.firstOrNull { where(it.second) }?.first
    }

    fun promotePiece(at: PiecePosition, to: PieceType): Board {
        val oldPiece = pieceAt(at) ?: return this
        val newPieces = pieces.map { it.toMutableList() }.toMutableList()
        newPieces[at.y][at.x] = oldPiece.copy(type = to)

        return Board(newPieces.map { it.toList() }.toList())
    }

    fun removePiece(at: PiecePosition): Board {
        val newPieces = pieces.map { it.toMutableList() }.toMutableList()
        newPieces[at.y][at.x] = null

        return Board(newPieces.map { it.toList() }.toList())
    }

    fun piecesExistBetweenPositions(between: PiecePosition, and: PiecePosition): Boolean {
        val step = PieceDelta(
            x = if (between.x > and.x) -1 else if (between.x < and.x) 1 else 0,
            y = if (between.y > and.y) -1 else if (between.y < and.y) 1 else 0
        )

        var position = between
        position += step
        while (position != and) {
            if (pieceAt(position) != null) {
                return true
            }
            position += step
        }
        return false
    }


}