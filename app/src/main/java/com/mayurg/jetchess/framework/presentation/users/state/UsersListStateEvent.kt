package com.mayurg.jetchess.framework.presentation.users.state

import com.mayurg.jetchess.business.domain.state.StateEvent

/**
 * Created On 17/11/2021
 * @author Mayur Gajra
 */
sealed class UsersListStateEvent : StateEvent {

    object GetUsersEvent : UsersListStateEvent() {
        override fun errorInfo(): String {
            return "Error fetching user"
        }

        override fun eventName(): String {
            return "GetUsersEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }

    class SendChallengeEvent(
        val toId: String
    ) : UsersListStateEvent() {
        override fun errorInfo(): String {
            return "Error sending challenge"
        }

        override fun eventName(): String {
            return "SendChallengeEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }

}