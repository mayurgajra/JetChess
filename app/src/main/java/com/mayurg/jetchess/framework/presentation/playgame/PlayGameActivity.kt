package com.mayurg.jetchess.framework.presentation.playgame

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.playgame.PlayGameViewModel.SendWsEvent.PieceMovedEvent
import com.mayurg.jetchess.framework.presentation.playgame.boardview.BoardMainContainer
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Move
import com.mayurg.jetchess.framework.presentation.playgame.gameview.MoveResult
import com.mayurg.jetchess.framework.presentation.playgame.pieceview.PiecePosition
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameStateEvent
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest


@OptIn(FlowPreview::class)
@AndroidEntryPoint
class PlayGameActivity : BaseActivity() {

    val viewModel: PlayGameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val roomId = intent.extras?.getString("roomId") ?: ""
        val shouldRotate = intent.extras?.getBoolean("shouldRotate", false) ?: false
        viewModel.isMakingAMove.value = intent.extras?.getBoolean("isMakingAMove", false) ?: false

        if (!TextUtils.isEmpty(roomId)) {
            viewModel.setStateEvent(PlayGameStateEvent.JoinRoomEvent(roomId))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.connectionEvent.collectLatest { event ->
                when (event) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        viewModel.sendWsEvent(
                            PlayGameViewModel.SendWsEvent.JoinRoomHandShakeEvent(roomId)
                        )
                    }

                    is WebSocket.Event.OnConnectionFailed -> {
                        event.throwable.printStackTrace()
                    }

                    is WebSocket.Event.OnConnectionClosed -> {

                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.socketEvent.collectLatest { event ->
                when (event) {
                    is PlayGameViewModel.SocketEvent.PieceMoved -> {
                        try {
                            val moveResult = viewModel.moveResult.value as MoveResult.Success
                            val game = moveResult.game
                            val move = event.move
                            viewModel.moveResult.value =
                                game.doMove(move.fromPosition, move.toPosition)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    is PlayGameViewModel.SocketEvent.PlayerJoined -> {
                        viewModel.setPlayerJoined(event.playerFE)
                    }
                }

            }
        }

        setContent {
            AppTheme {
                PlayGameView(roomId, shouldRotate)
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    fun PlayGameView(roomId: String, shouldRotate: Boolean) {
        val moveResult = viewModel.moveResult.observeAsState()
        var selection: PiecePosition? by remember { mutableStateOf(null) }
        val currentRotation = if (shouldRotate) 180f else 0f

        val loggedInPlayer = viewModel.loggedInPlayer.observeAsState()
        val oppositePlayer = viewModel.oppositePlayer.observeAsState()
        val isMakingAMove = viewModel.isMakingAMove.observeAsState()

        when (val result = moveResult.value) {
            is MoveResult.Success -> {
                val game = result.game

                val onSelect: (PiecePosition) -> Unit = {
                    if (isMakingAMove.value == true) {
                        val sel = selection
                        if (game.canSelect(it)) {
                            selection = it
                        } else if (sel != null && game.canMove(sel, it)) {
                            viewModel.moveResult.value = game.doMove(sel, it)
                            viewModel.sendWsEvent(PieceMovedEvent(roomId, Move(sel, it)))
                            selection = null
                        }
                    }
                }

                Column {
                    Text(
                        text = oppositePlayer.value?.playerName.orEmpty(),
                        color = if (isMakingAMove.value == false) Color.Cyan else Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                    BoardMainContainer(
                        modifier = Modifier.rotate(currentRotation),
                        game = game,
                        selection = selection,
                        moves = game.movesForPieceAt(selection),
                        didTap = onSelect,
                        currentRotation = currentRotation
                    )
                    Text(
                        text = "${loggedInPlayer.value?.playerName.orEmpty()} (You)",
                        color = if (isMakingAMove.value == true) Color.Cyan else Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }


}