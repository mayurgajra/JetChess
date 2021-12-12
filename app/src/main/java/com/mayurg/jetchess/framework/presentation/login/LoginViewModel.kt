package com.mayurg.jetchess.framework.presentation.login

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.users.UserInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.login.state.LoginStateEvent
import com.mayurg.jetchess.framework.presentation.login.state.LoginUserViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userInteractors: UserInteractors
) : BaseViewModel<LoginUserViewState>() {

    override fun handleNewData(data: LoginUserViewState) {
        data.let { viewState ->
            viewState.loginResponseModel?.let {
                val state = getCurrentViewStateOrNew()
                state.loginResponseModel = it
                setViewState(state)
            }

            viewState.userDataSaved?.let {
                val state = getCurrentViewStateOrNew()
                state.userDataSaved = it
                setViewState(state)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<LoginUserViewState>?> = when (stateEvent) {

            is LoginStateEvent.LoginUser -> {
                userInteractors.loginUser.loginUser(
                    email = stateEvent.email,
                    password = stateEvent.password,
                    stateEvent = stateEvent
                )
            }

            is LoginStateEvent.SaveUserData -> {
                userInteractors.saveUserInfo.saveUserInfo(stateEvent.user)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): LoginUserViewState {
        return LoginUserViewState()
    }
}