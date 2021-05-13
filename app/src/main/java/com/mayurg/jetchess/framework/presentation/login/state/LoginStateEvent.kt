package com.mayurg.jetchess.framework.presentation.login.state

import com.mayurg.jetchess.business.domain.state.StateEvent

sealed class LoginStateEvent : StateEvent {

    class LoginUser(
        val email: String,
        val password: String,
    ) : LoginStateEvent() {
        override fun errorInfo(): String {
            return "Error logging in user"
        }

        override fun eventName(): String {
            return "RegisterUser"
        }

        override fun shouldDisplayProgressBar() = true
    }
}