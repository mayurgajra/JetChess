package com.mayurg.jetchess.framework.presentation.challenges

import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.interactors.challenges.ChallengesInteractors
import com.mayurg.jetchess.framework.presentation.base.BaseViewModel
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListStateEvent
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */
@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
@HiltViewModel
class ChallengesListViewModel @Inject constructor(
    private val challengesInteractors: ChallengesInteractors
) : BaseViewModel<ChallengesListViewState>() {


    override fun handleNewData(data: ChallengesListViewState) {
        data.let { viewState ->
            viewState.list?.let {
                val state = getCurrentViewStateOrNew()
                state.list = it
                setViewState(state)
            }

            viewState.statusChangeResult?.let { stateResult ->
                val state = getCurrentViewStateOrNew()
                state.statusChangeResult = stateResult
                state.roomId = data.roomId
                setViewState(state)
            }

        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<ChallengesListViewState>?> = when (stateEvent) {

            is ChallengesListStateEvent.GetChallengesStateEvent -> {
                challengesInteractors.challengesList.getChallengesList(stateEvent)
            }

            is ChallengesListStateEvent.AcceptRejectStateEvent -> {
                challengesInteractors.acceptRejectChallenge.acceptRejectChallenge(stateEvent)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): ChallengesListViewState {
        return ChallengesListViewState()
    }


}