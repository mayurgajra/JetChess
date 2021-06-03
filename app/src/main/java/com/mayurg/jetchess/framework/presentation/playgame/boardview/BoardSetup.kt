package com.mayurg.jetchess.framework.presentation.playgame.boardview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.Piece
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition

@Composable
fun BoardMainContainer() {
    Box {
        BoardBg()
//        BoardPiecesSetup(pieces = ,)
    }
}

@Composable
private fun BoardBg() {
    val darkSquare = Color(0xFF779556)
    val lightSquare = Color(0xFFEBECD0)
    Column {
        for (i in 0 until 8) {
            Row {
                for (j in 0 until 8) {
                    val isLightSquare = i % 2 == j % 2
                    val squareColor = if (isLightSquare) lightSquare else darkSquare
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(squareColor)
                    ) {
                        Text(text = "${i + j}")
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardPiecesSetup(
    modifier: Modifier = Modifier,
    pieces: List<Pair<PiecePosition, Piece>>
) {
    val constrains = constrainsForGivenPieces(pieces)
    ConstraintLayout(constrains,modifier) {
        pieces.forEach { (_, piece) ->
            PieceView(piece = piece, modifier = Modifier.layoutId(piece.id))
        }
    }
}

@Composable
fun PieceView(piece: Piece, modifier: Modifier = Modifier) {
    Image(painter = painterResource(id = piece.getPieceDrawable()),
        contentDescription = "", modifier = modifier.padding(4.dp))
}

private val INITIAL_BOARD = listOf(
    listOf("BR0", "BN1", "BB2", "BQ3", "BK4", "BB5", "BN6", "BR7").map { Piece.pieceFromString(it) },
    listOf("BP0", "BP1", "BP2", "BP3", "BP4", "BP5", "BP6", "BP7").map { Piece.pieceFromString(it) },
    listOf(null, null, null, null, null, null, null, null),
    listOf(null, null, null, null, null, null, null, null),
    listOf(null, null, null, null, null, null, null, null),
    listOf(null, null, null, null, null, null, null, null),
    listOf("WP0", "WP1", "WP2", "WP3", "WP4", "WP5", "WP6", "WP7").map { Piece.pieceFromString(it) },
    listOf("WR0", "WN1", "WB2", "WQ3", "WK4", "WB5", "WN6", "WR7").map { Piece.pieceFromString(it) }
)

fun constrainsForGivenPieces(pieces: List<Pair<PiecePosition, Piece>>): ConstraintSet {
    return ConstraintSet {
        val horizontalGuideLines = (0..8).map { createGuidelineFromAbsoluteLeft(it.toFloat() / 8f) }
        val verticalGuideLines = (0..8).map { createGuidelineFromTop(it.toFloat() / 8f) }
        pieces.forEach { (position, piece) ->
            val pieceRef = createRefFor(piece.id)
            constrain(pieceRef) {
                top.linkTo(verticalGuideLines[position.y])
                bottom.linkTo(verticalGuideLines[position.y + 1])
                start.linkTo(horizontalGuideLines[position.x])
                end.linkTo(horizontalGuideLines[position.x + 1])
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    }
}
