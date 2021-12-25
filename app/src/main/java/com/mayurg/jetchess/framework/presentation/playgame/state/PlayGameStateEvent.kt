package com.mayurg.jetchess.framework.presentation.playgame.state

import com.mayurg.jetchess.business.domain.state.StateEvent

/**
 * Created On 25/12/2021
 * @author Mayur Gajra
 */
sealed class PlayGameStateEvent: StateEvent {

    class JoinRoomEvent(val roomId: String) : PlayGameStateEvent() {
        override fun errorInfo(): String {
            return "Error joining room"
        }

        override fun eventName(): String {
            return "JoinRoomEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}
