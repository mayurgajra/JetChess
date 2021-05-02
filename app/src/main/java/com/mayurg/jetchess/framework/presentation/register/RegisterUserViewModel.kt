package com.mayurg.jetchess.framework.presentation.register

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.UserInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.register.state.RegisterStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
class RegisterUserViewModel
@Inject constructor(
    private val userInteractors: UserInteractors
) : BaseViewModel<RegisterStateEvent>() {

    override fun handleNewData(data: RegisterStateEvent) {
        TODO("Not yet implemented")
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<RegisterStateEvent>?> = when (stateEvent) {

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

    override fun initNewViewState(): RegisterStateEvent {
        TODO("Not yet implemented")
    }

}