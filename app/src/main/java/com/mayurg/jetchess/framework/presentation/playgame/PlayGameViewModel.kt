package com.mayurg.jetchess.framework.presentation.playgame

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.playgame.PlayGameInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameStateEvent
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created On 25/12/2021
 * @author Mayur Gajra
 */
@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
@HiltViewModel
class PlayGameViewModel @Inject constructor(
    private val playGameInteractors: PlayGameInteractors
) : BaseViewModel<PlayGameViewState>() {

    override fun handleNewData(data: PlayGameViewState) {
        data.let { viewState ->
            val state = getCurrentViewStateOrNew()
            state.hasJoinedGameRoom = viewState.hasJoinedGameRoom
            setViewState(state)
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
}