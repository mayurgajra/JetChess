package com.mayurg.jetchess.framework.presentation.playgame.boardview

import com.mayurg.jetchess.framework.presentation.playgame.gameview.Move
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.Piece
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PieceType

val INITIAL_BOARD = listOf(
    listOf("BR0", "BN1", "BB2", "BQ3", "BK4", "BB5", "BN6", "BR7").map { Piece.pieceFromId(it) },
    listOf("BP0", "BP1", "BP2", "BP3", "BP4", "BP5", "BP6", "BP7").map { Piece.pieceFromId(it) },
    listOf(null, null, null, null, null, null, null, null),
    listOf(null, null, null, null, null, null, null, null),
    listOf(null, null, null, null, null, null, null, null),
    listOf(null, null, null, null, null, null, null, null),
    listOf("WP0", "WP1", "WP2", "WP3", "WP4", "WP5", "WP6", "WP7").map { Piece.pieceFromId(it) },
    listOf("WR0", "WN1", "WB2", "WQ3", "WK4", "WB5", "WN6", "WR7").map { Piece.pieceFromId(it) }
)
val STARTING_PIECES = INITIAL_BOARD.flatten().filterNotNull()

data class Board(val pieces: List<List<Piece?>> = INITIAL_BOARD) {
    companion object {
        private val ALL_POSITIONS = (0 until 8).flatMap { y ->
            (0 until 8).map { x -> PiecePosition(x, y) }
        }

        fun fromHistory(history: List<Move>): Board {
            var board = Board()
            history.forEach {
                board = board.movePiece(it.fromPosition, it.toPosition)
            }

            return board
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
        newPieces[from.y][from.x] = null

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
        val oldPiece = pieceAt(at) ?: return this
        val newPieces = pieces.map { it.toMutableList() }.toMutableList()
        newPieces[at.y][at.x] = null

        return Board(newPieces.map { it.toList() }.toList())
    }




}