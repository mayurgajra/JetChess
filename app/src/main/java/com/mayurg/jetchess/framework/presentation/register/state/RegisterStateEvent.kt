package com.mayurg.jetchess.framework.presentation.register.state

import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.business.domain.state.StateMessage

sealed class RegisterStateEvent : StateEvent {

    class RegisterUser(
        val fullName: String,
        val mobile: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : RegisterStateEvent() {
        override fun errorInfo(): String {
            return "Error registering user"
        }

        override fun eventName(): String {
            return "RegisterUser"
        }

        override fun shouldDisplayProgressBar() = true
    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ): RegisterStateEvent(){

        override fun errorInfo(): String {
            return "Error creating a new state message."
        }

        override fun eventName(): String {
            return "CreateStateMessageEvent"
        }

        override fun shouldDisplayProgressBar() = false
    }
}