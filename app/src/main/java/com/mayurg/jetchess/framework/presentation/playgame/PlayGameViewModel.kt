package com.mayurg.jetchess.framework.presentation.playgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.playgame.PlayGameInteractors
import com.mayurg.jetchess.framework.datasource.network.ws.GameApi
import com.mayurg.jetchess.framework.datasource.network.ws.models.BaseModel
import com.mayurg.jetchess.framework.datasource.network.ws.models.GameMove
import com.mayurg.jetchess.framework.datasource.network.ws.models.JoinRoomHandshake
import com.mayurg.jetchess.framework.datasource.network.ws.models.PlayerFE
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Game
import com.mayurg.jetchess.framework.presentation.playgame.gameview.Move
import com.mayurg.jetchess.framework.presentation.playgame.gameview.MoveResult
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameStateEvent
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameViewState
import com.mayurg.jetchess.util.DispatcherProvider
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/**
 * Created On 25/12/2021
 * @author Mayur Gajra
 */
@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
@HiltViewModel
class PlayGameViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val playGameInteractors: PlayGameInteractors,
    private val gameApi: GameApi,
    private val jetChessLocalDataSource: JetChessLocalDataSource
) : BaseViewModel<PlayGameViewState>() {

    sealed class SocketEvent {
        class PieceMoved(val move: Move) : SocketEvent()
        class PlayerJoined(val playerFE: PlayerFE) : SocketEvent()
    }

    sealed class SendWsEvent {
        class JoinRoomHandShakeEvent(val roomId: String) : SendWsEvent()

        class PieceMovedEvent(val roomId: String, val move: Move) : SendWsEvent()
    }

    /**
     * [connectionEventChannel] is used to manage events related to connections with the server.
     * [WebSocket.Event] are sent to notify whether sockets are connected or closed.
     */
    private val connectionEventChannel = Channel<WebSocket.Event>()
    val connectionEvent = connectionEventChannel.receiveAsFlow().flowOn(dispatchers.io)

    /**
     * [socketEventChannel] is used to pass the events of the game namely [SocketEvent]
     * When client is connected to the server via [WebSocket]
     */
    private val socketEventChannel = Channel<SocketEvent>()
    val socketEvent = socketEventChannel.receiveAsFlow().flowOn(dispatchers.io)

    var moveResult = MutableLiveData<MoveResult>(MoveResult.Success(Game()))

    var loggedInPlayer = MutableLiveData(PlayerFE())
    var oppositePlayer = MutableLiveData(PlayerFE())

    var isMakingAMove = MutableLiveData(false)

    init {
        observeEvents()
        observeBaseModels()
    }

    override fun handleNewData(data: PlayGameViewState) {
        data.let { viewState ->
            viewState.hasJoinedGameRoom?.let {
                val state = getCurrentViewStateOrNew()
                state.hasJoinedGameRoom = it
                setViewState(state)
            }

        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<PlayGameViewState>?> = when (stateEvent) {

            is PlayGameStateEvent.JoinRoomEvent -> {
                playGameInteractors.joinGameRoom.joinGameRoom(stateEvent)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): PlayGameViewState {
        return PlayGameViewState()
    }

    fun sendWsEvent(event: SendWsEvent) {
        when (event) {
            is SendWsEvent.JoinRoomHandShakeEvent -> {
                viewModelScope.launch {
                    val user = jetChessLocalDataSource.getUserInfo()
                    user?.let {
                        sendBaseModel(
                            JoinRoomHandshake(it.fullName, event.roomId, it.id)
                        )
                    }

                }

            }

            is SendWsEvent.PieceMovedEvent -> {
                viewModelScope.launch {
                    val user = jetChessLocalDataSource.getUserInfo()
                    user?.let {
                        sendBaseModel(
                            GameMove(it.id, event.roomId, event.move)
                        )
                        withContext(Dispatchers.Main){
                            isMakingAMove.value = false
                        }
                    }

                }
            }
        }
    }


    /**
     * Observe the [WebSocket.Event] & pass them through [connectionEventChannel] for the UI
     */
    private fun observeEvents() {
        viewModelScope.launch(dispatchers.io) {
            gameApi.observeEvents().collect { event ->
                connectionEventChannel.send(event)
            }
        }
    }

    private fun observeBaseModels() {
        viewModelScope.launch(dispatchers.io) {
            gameApi.observeBaseModels().collect { data ->
                when (data) {
                    is GameMove -> {
                        socketEventChannel.send(SocketEvent.PieceMoved(data.move))
                        withContext(Dispatchers.Main){
                            isMakingAMove.value = true
                        }
                    }

                    is PlayerFE -> {
                        socketEventChannel.send(SocketEvent.PlayerJoined(data))
                    }
                }
            }
        }
    }

    /**
     * Send the [BaseModel] through [gameApi] socket
     *
     * @param data of [BaseModel] to send through socket
     */
    private fun sendBaseModel(data: BaseModel) {
        viewModelScope.launch(dispatchers.io) {
            gameApi.sendBaseModel(data)
        }
    }

    fun setPlayerJoined(playerFE: PlayerFE) {
        viewModelScope.launch {
            val user = jetChessLocalDataSource.getUserInfo()
            user?.let {
                if (playerFE.playerId == it.id) {
                    loggedInPlayer.postValue(playerFE)
                } else {
                    oppositePlayer.postValue(playerFE)
                }
            }
        }

    }
}