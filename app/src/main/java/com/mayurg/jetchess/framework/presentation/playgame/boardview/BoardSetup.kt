package com.mayurg.jetchess.framework.presentation.playgame.boardview

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardColors.lastMoveColor
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardColors.lightSquare
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Game
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Move
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.Piece
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PieceColor
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition

@Composable
fun BoardMainContainer(
    modifier: Modifier = Modifier,
    game: Game,
    selection: PiecePosition?,
    moves: List<PiecePosition>,
    didTap: (PiecePosition) -> Unit
) {
    Box(modifier) {
        val board = game.board

        val dangerPositions = listOf(
            PieceColor.White,
            PieceColor.Black
        ).mapNotNull { if (game.kingIsInCheck(it)) game.kingPosition(it) else null }

        BoardBg(game.historyMovesList.lastOrNull(), selection, dangerPositions, didTap)
        BoardPiecesSetup(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f),
            pieces = board.allPieces
        )
        MovesOverlay(board, moves)
    }
}

@Composable
private fun BoardBg(
    lastMove: Move?,
    selection: PiecePosition?,
    dangerPositions: List<PiecePosition>,
    didTap: (PiecePosition) -> Unit
) {
    Column {
        for (y in 0 until 8) {
            Row {
                for (x in 0 until 8) {
                    val position = PiecePosition(x, y)
                    val isLightSquare = y % 2 == x % 2
                    val squareColor =
                        if (lastMove?.contains(position) == true || position == selection) {
                            lastMoveColor
                        } else if (dangerPositions.contains(position)) {
                            BoardColors.kingInCheckColor
                        } else if (isLightSquare) lightSquare else darkSquare

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(squareColor)
                            .clickable {
                                didTap(position)
                            }

                    ) {
                        if (y == 7) {
                            Text(
                                text = "${'a' + x}",
                                modifier = Modifier.align(Alignment.BottomEnd),
                                style = MaterialTheme.typography.caption,
                                color = Color.Black.copy(0.5f)
                            )
                        }
                        if (x == 0) {
                            Text(
                                text = "${8 - y}",
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
        for (y in 0 until 8) {
            Row {
                for (x in 0 until 8) {
                    val position = PiecePosition(x, y)
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
