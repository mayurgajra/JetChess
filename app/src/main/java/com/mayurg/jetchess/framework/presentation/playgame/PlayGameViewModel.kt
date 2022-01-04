package com.mayurg.jetchess.framework.presentation.playgame

import androidx.lifecycle.viewModelScope
import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.playgame.PlayGameInteractors
import com.mayurg.jetchess.framework.datasource.network.ws.DrawingApi
import com.mayurg.jetchess.framework.datasource.network.ws.models.BaseModel
import com.mayurg.jetchess.framework.datasource.network.ws.models.JoinRoomHandshake
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameStateEvent
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameViewState
import com.mayurg.jetchess.util.DispatcherProvider
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
    private val drawingApi: DrawingApi,
    private val jetChessLocalDataSource: JetChessLocalDataSource
) : BaseViewModel<PlayGameViewState>() {

    sealed class SocketEvent {

    }

    sealed class SendWsEvent {
        class JoinRoomHandShakeEvent(val roomId: String) : SendWsEvent()
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

    init {
        observeEvents()
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
        }
    }


    /**
     * Observe the [WebSocket.Event] & pass them through [connectionEventChannel] for the UI
     */
    private fun observeEvents() {
        viewModelScope.launch(dispatchers.io) {
            drawingApi.observeEvents().collect { event ->
                connectionEventChannel.send(event)
            }
        }
    }

    /**
     * Send the [BaseModel] through [drawingApi] socket
     *
     * @param data of [BaseModel] to send through socket
     */
    fun sendBaseModel(data: BaseModel) {
        viewModelScope.launch(dispatchers.io) {
            drawingApi.sendBaseModel(data)
        }
    }
}