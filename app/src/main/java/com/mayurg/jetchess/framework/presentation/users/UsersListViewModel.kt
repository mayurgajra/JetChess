package com.mayurg.jetchess.framework.presentation.users

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.users.UserInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.users.state.UserListViewState
import com.mayurg.jetchess.framework.presentation.users.state.UsersListStateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created On 17/11/2021
 * @author Mayur Gajra
 */
@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val userInteractors: UserInteractors
) : BaseViewModel<UserListViewState>() {


    override fun handleNewData(data: UserListViewState) {
        data.let { viewState ->
            viewState.list?.let {
                val state = getCurrentViewStateOrNew()
                state.list = it
                setViewState(state)
            }

            viewState.sendChallengeResponse?.let {
                val state = getCurrentViewStateOrNew()
                state.sendChallengeResponse = it
                setViewState(state)
                it.challengeId?.let { id ->
                    setStateEvent(UsersListStateEvent.CreateGameRoomEvent(id))
                }
            }

            viewState.roomId?.let {
                val state = getCurrentViewStateOrNew()
                state.roomId = it
                setViewState(state)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<UserListViewState>?> = when (stateEvent) {

            is UsersListStateEvent.GetUsersEvent -> {
                userInteractors.usersList.getUsers(stateEvent)
            }

            is UsersListStateEvent.SendChallengeEvent -> {
                userInteractors.sendChallenge.sendChallenge(stateEvent)
            }

            is UsersListStateEvent.CreateGameRoomEvent -> {
                userInteractors.createGameRoom.createGameRoom(stateEvent)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): UserListViewState {
        return UserListViewState()
    }

}