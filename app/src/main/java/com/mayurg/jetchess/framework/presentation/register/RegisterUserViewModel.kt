package com.mayurg.jetchess.framework.presentation.register

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.UserInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.register.state.RegisterStateEvent
import com.mayurg.jetchess.framework.presentation.register.state.RegisterUserViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class RegisterUserViewModel
@Inject constructor(
    private val userInteractors: UserInteractors
) : BaseViewModel<RegisterUserViewState>() {


    override fun handleNewData(data: RegisterUserViewState) {
        data.let { viewState ->
            viewState.baseResponseModel?.let {
                val state = getCurrentViewStateOrNew()
                state.baseResponseModel = it
                setViewState(state)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<RegisterUserViewState>?> = when (stateEvent) {

            is RegisterStateEvent.RegisterUser -> {
                userInteractors.registerUser.registerUser(
                    fullName = stateEvent.fullName,
                    email = stateEvent.email,
                    mobile = stateEvent.mobile,
                    password = stateEvent.password
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): RegisterUserViewState {
        return RegisterUserViewState()
    }

}