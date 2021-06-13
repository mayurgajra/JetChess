package com.mayurg.jetchess.framework.presentation.playgame.gameview

import com.mayurg.jetchess.framework.presentation.playgame.boardview.Board
import com.mayurg.jetchess.framework.presentation.playgame.boardview.STARTING_PIECES
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.*
import kotlin.math.abs

data class Game(val board: Board = Board(), val historyMovesList: List<Move> = listOf()) {
    val gameState: GameState
        get() {
            val color = turn
            val canMove = allMovesForColor(color).find {
                val newBoard = doMove(it.fromPosition, it.toPosition)
                (newBoard is MoveResult.Success) && !newBoard.game.kingIsInCheck(color)
            } != null
            if (kingIsInCheck(color)) {
                return if (canMove) GameState.CHECK else GameState.CHECKMATE
            }
            return if (canMove) GameState.IDLE else GameState.STALEMATE
        }

    val turn: PieceColor
        get() = historyMovesList.lastOrNull()
            ?.let { board.pieceAt(it.toPosition)?.pieceColor?.other() } ?: PieceColor.White

    private fun allMovesForPosition(position: PiecePosition): Sequence<Move> {
        return board.allPositions.asSequence()
            .map { Move(position, it) }
            .filter { canMove(it.fromPosition, it.toPosition) }
    }

    fun allMovesForColor(color: PieceColor): Sequence<Move> {
        return board.allPieces.asSequence().mapNotNull { (postion, piece) ->
            if (piece.pieceColor == color) postion else null
        }.flatMap { allMovesForPosition(it) }
    }

    private fun pieceThreatenedAtPosition(position: PiecePosition): Boolean {
        return board.allPositions.find { canMove(from = it, to = position) } != null
    }


    fun kingPosition(color: PieceColor): PiecePosition? {
        return board.firstPositionOfPiece { it.type is PieceType.King && it.pieceColor == color }
    }


    fun kingIsInCheck(color: PieceColor): Boolean {
        val kingPosition = kingPosition(color) ?: return false
        return pieceThreatenedAtPosition(kingPosition)
    }

    fun canSelect(position: PiecePosition): Boolean {
        return board.pieceAt(position)?.pieceColor == turn
    }


    fun canMove(from: PiecePosition, to: PiecePosition): Boolean {
        val piece = board.pieceAt(from) ?: return false

        val delta = to - from
        val other = board.pieceAt(to)
        if (other != null) {
            if (other.pieceColor == piece.pieceColor) {
                return false
            }

            if (piece.type is PieceType.Pawn) {
                return pawnCanTake(from, delta)
            }
        }

        when (piece.type) {
            is PieceType.Pawn -> {
                if (enPassantTakePermitted(from, to)) {
                    return true
                }
                if (delta.x != 0) {
                    return false
                }
                return when (piece.pieceColor) {
                    is PieceColor.White -> {
                        if (from.y == 6) {
                            listOf(-1, -2).contains(delta.y) &&
                                    !board.piecesExistBetweenPositions(from, to)
                        } else {
                            delta.y == -1
                        }
                    }
                    is PieceColor.Black -> {
                        if (from.y == 1) {
                            listOf(1, 2).contains(delta.y) &&
                                    !board.piecesExistBetweenPositions(from, to)
                        } else {
                            delta.y == 1
                        }
                    }
                }
            }

            is PieceType.Rook -> {
                return (delta.x == 0 || delta.y == 0) && !board.piecesExistBetweenPositions(
                    from,
                    to
                )
            }

            is PieceType.Bishop -> {
                return abs(delta.x) == abs(delta.y) && !board.piecesExistBetweenPositions(from, to)
            }

            is PieceType.Queen -> {
                return (delta.x == 0 || delta.y == 0 || abs(delta.x) == abs(delta.y))
                        && !board.piecesExistBetweenPositions(from, to)
            }

            is PieceType.King -> {
                if (abs(delta.x) <= 1 && abs(delta.y) <= 1) return true
                return castlingPermitted(from, to)
            }

            is PieceType.Knight -> {
                return listOf(
                    PieceDelta(x = 1, y = 2),
                    PieceDelta(x = -1, y = 2),
                    PieceDelta(x = 2, y = 1),
                    PieceDelta(x = -2, y = 1),
                    PieceDelta(x = 1, y = -2),
                    PieceDelta(x = -1, y = -2),
                    PieceDelta(x = 2, y = -1),
                    PieceDelta(x = -2, y = -1)
                ).contains(delta)
            }
        }


    }

    fun doMove(from: PiecePosition, to: PiecePosition): MoveResult {
        val oldGame = this.copy()
        val newGame = move(from, to)
        val wasInCheck = newGame.kingIsInCheck(oldGame.turn)

        if (wasInCheck) {
            return MoveResult.Success(oldGame)
        }

        val wasPromoted = newGame.canPromotePieceAt(to)

        if (wasPromoted) {
            return MoveResult.Promotion { promoteTo ->
                MoveResult.Success(newGame.promotePieceAt(to, promoteTo))
            }
        }

        return MoveResult.Success(newGame)

    }

    private fun move(from: PiecePosition, to: PiecePosition): Game {
        val intermediateBoard = if (board.pieceAt(from)?.type == PieceType.King
            && abs(to.x - from.x) > 1
        ) {
            val kingSide = (to.x == 6)
            val rookPosition = PiecePosition(if (kingSide) 7 else 0, to.y)
            val rookDestination = PiecePosition(if (kingSide) 5 else 3, to.y)
            board.movePiece(rookPosition, rookDestination)
        } else if (board.pieceAt(from)?.type == PieceType.Pawn && enPassantTakePermitted(
                from,
                to
            )
        ) {
            board.removePiece(PiecePosition(to.x, to.y - (to.y - from.y)))
        } else {
            board
        }
        return Game(
            board = intermediateBoard.movePiece(from, to), historyMovesList = historyMovesList +
                    listOf(Move(from, to))
        )
    }

    fun movesForPieceAt(position: PiecePosition?): List<PiecePosition> {
        if (position == null) return emptyList()
        return board.allPositions.filter { canMove(position, it) }
    }

    private fun pawnCanTake(from: PiecePosition, withDelta: PieceDelta): Boolean {
        val pawn = board.pieceAt(from) ?: return false
        if (abs(withDelta.x) != 1 || pawn.type != PieceType.Pawn) {
            return false
        }

        return if (pawn.pieceColor is PieceColor.White) {
            withDelta.y == -1
        } else {
            withDelta.y == 1
        }
    }

    private fun canPromotePieceAt(position: PiecePosition): Boolean {
        val pawn = board.pieceAt(position)
        if (pawn?.type !is PieceType.Pawn) return false
        return (pawn.pieceColor == PieceColor.White && position.y == 0)
                || (pawn.pieceColor == PieceColor.Black && position.y == 7)
    }

    private fun promotePieceAt(position: PiecePosition, toPieceType: PieceType): Game {
        return Game(board.promotePiece(position, toPieceType), historyMovesList)
    }

    private fun pieceHasMoved(at: PiecePosition): Boolean {
        return historyMovesList.find { it.fromPosition == at } != null
    }

    private fun positionIsThreatened(position: PiecePosition, by: PieceColor): Boolean {
        return board.allPieces.find { (from, piece) ->
            if (piece.pieceColor != by) return@find false
            if (piece.type == PieceType.Pawn) return@find pawnCanTake(from, position - from)
            return canMove(from, position)
        } != null
    }

    private fun castlingPermitted(from: PiecePosition, to: PiecePosition): Boolean {
        val piece = board.pieceAt(from) ?: return false
        if (piece.type != PieceType.King) return false
        val kingsRow = if (piece.pieceColor == PieceColor.Black) 0 else 7
        if (!(from.y == kingsRow && to.y == kingsRow && from.x == 4 && listOf(
                2,
                6
            ).contains(to.x))
        ) return false

        val kingsPosition = PiecePosition(4, kingsRow)
        if (pieceHasMoved(kingsPosition)) return false

        val isKingSide = to.x == 6
        val rookPosition = PiecePosition(if (isKingSide) 7 else 0, kingsRow)
        if (pieceHasMoved(rookPosition)) return false

        return ((if (isKingSide) 5..6 else 1..3).map { board.pieceAt(PiecePosition(it, kingsRow)) }
            .find { it != null } == null)
                && (if (isKingSide) 4..6 else 2..4).map {
            positionIsThreatened(
                PiecePosition(
                    it,
                    kingsRow
                ),
                by = this.turn.other()
            )
        }.find { it } == null

    }

    private fun enPassantTakePermitted(from: PiecePosition, to: PiecePosition): Boolean {
        board.pieceAt(from) ?: return false
        if (!pawnCanTake(from, to - from)) return false

        val lastMove = historyMovesList.lastOrNull() ?: return false
        if (lastMove.toPosition.x != to.x) return false

        val lastPiece = board.pieceAt(lastMove.toPosition) ?: return false
        if (lastPiece.type != PieceType.Pawn || lastPiece.pieceColor == turn) return false

        return when (lastPiece.pieceColor) {
            is PieceColor.White -> {
                lastMove.fromPosition.y == to.y + 1 && lastMove.toPosition.y == to.y - 1
            }
            is PieceColor.Black -> {
                lastMove.fromPosition.y == to.y - 1 && lastMove.toPosition.y == to.y + 1
            }
        }
    }

    fun valuesFor(color: PieceColor): Int {
        return board.allPieces.filter { it.second.pieceColor == color }.map {
            it.second.type.points
        }.sum()
    }

    fun capturedPiecesFor(color: PieceColor): List<Piece> {
        val startingPieces = STARTING_PIECES.filter { it.pieceColor == color }.map { it.id }.toSet()
        val currentPieces =
            board.allPieces.map { it.second }.filter { it.pieceColor == color }.map {
                it.id
            }.toSet()

        val capturedPieces = startingPieces - currentPieces
        return capturedPieces.map { Piece.pieceFromId(it) }
    }
}
