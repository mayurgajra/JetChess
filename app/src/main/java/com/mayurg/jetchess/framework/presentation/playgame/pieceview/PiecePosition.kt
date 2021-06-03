package com.mayurg.jetchess.framework.presentation.playgame.pieceview

data class PiecePosition(val x: Int, val y: Int) {
    operator fun plus(other: PiecePosition): PieceDelta {
        return PieceDelta(this.x + other.x, this.y + other.y)
    }

    operator fun minus(other: PiecePosition): PieceDelta {
        return PieceDelta(this.x - other.x, this.y - other.y)
    }


    operator fun plus(other: PieceDelta): PiecePosition {
        return PiecePosition(this.x + other.x, this.y + other.y)
    }
}