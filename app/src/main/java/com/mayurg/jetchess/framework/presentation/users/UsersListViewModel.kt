package com.mayurg.jetchess.framework.presentation.users

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.UserInteractors
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
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<UserListViewState>?> = when (stateEvent) {

            is UsersListStateEvent.GetUsersEvent -> {
                userInteractors.usersList.getUsers(stateEvent)
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