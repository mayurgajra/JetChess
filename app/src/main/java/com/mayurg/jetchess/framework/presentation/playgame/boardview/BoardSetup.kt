package com.mayurg.jetchess.framework.presentation.playgame.boardview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardColors.darkSquare
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardColors.lightSquare
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.Piece
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition

@Composable
fun BoardMainContainer() {
    Box {
        val board = Board()
        BoardBg()
        BoardPiecesSetup(
            pieces = board.allPieces,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
        )
        MovesOverlay(board, listOf())
    }
}

@Composable
private fun BoardBg() {
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
                        if (i == 7) {
                            Text(
                                text = "${'a' + j}",
                                modifier = Modifier.align(Alignment.BottomEnd),
                                style = MaterialTheme.typography.caption,
                                color = Color.Black.copy(0.5f)
                            )
                        }
                        if (j == 0) {
                            Text(
                                text = "${8 - i}",
                                modifier = Modifier.align(Alignment.TopStart),
                                style = MaterialTheme.typography.caption,
                                color = Color.Black.copy(0.5f)
                            )
                        }
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
    ConstraintLayout(constrains, modifier) {
        pieces.forEach { (_, piece) ->
            PieceView(piece = piece, modifier = Modifier.layoutId(piece.id))
        }
    }
}

@Composable
fun PieceView(piece: Piece, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = piece.getPieceDrawable()),
        contentDescription = "", modifier = modifier.padding(4.dp)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovesOverlay(board: Board, movesList: List<PiecePosition>) {
    Column {
        for (i in 0 until 8) {
            Row {
                for (j in 0 until 8) {
                    val position = PiecePosition(j, i)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        val piece = board.pieceAt(position)
                        val selected = movesList.contains(position)
                        androidx.compose.animation.AnimatedVisibility(
                            visible = selected,
                            modifier = Modifier.matchParentSize(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            val color = if (piece != null) BoardColors.pieceUnderAttackColor
                            else BoardColors.currentMoveColor
                            Box(
                                Modifier
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

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
