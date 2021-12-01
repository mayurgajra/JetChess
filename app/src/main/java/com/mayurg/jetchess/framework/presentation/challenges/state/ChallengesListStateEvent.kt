package com.mayurg.jetchess.framework.presentation.challenges.state

import com.mayurg.jetchess.business.domain.state.StateEvent

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */
sealed class ChallengesListStateEvent : StateEvent {

    object GetChallengesStateEvent : ChallengesListStateEvent() {
        override fun errorInfo(): String {
            return "Error fetching challenges"
        }

        override fun eventName(): String {
            return "GetChallengesStateEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return true
        }
    }

    class AcceptRejectStateEvent(val id: String, val status: String) : ChallengesListStateEvent() {

        override fun errorInfo(): String {
            return "Error setting the challenge status $status"
        }

        override fun eventName(): String {
            return "AcceptRejectStateEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return true
        }

    }


}