package com.mayurg.jetchess.framework.presentation.splash

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.users.UserInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.splash.state.SplashStateEvent
import com.mayurg.jetchess.framework.presentation.splash.state.SplashViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created On 13/12/2021
 * @author Mayur Gajra
 */
@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userInteractors: UserInteractors
) : BaseViewModel<SplashViewState>() {

    override fun handleNewData(data: SplashViewState) {
        data.let { viewState ->
            val state = getCurrentViewStateOrNew()
            state.userDataSaved = viewState.userDataSaved
            setViewState(state)
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<SplashViewState>?> = when (stateEvent) {

            is SplashStateEvent.GetUserData -> {
                userInteractors.getUserInfo.getUserInfo()
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): SplashViewState {
        return SplashViewState()
    }

}