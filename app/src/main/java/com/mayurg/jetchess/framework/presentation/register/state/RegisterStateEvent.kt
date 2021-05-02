package com.mayurg.jetchess.framework.presentation.register.state

import com.mayurg.jetchess.business.domain.state.StateEvent

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
}