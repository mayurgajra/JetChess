package com.mayurg.jetchess.framework.presentation.splash.state

import com.mayurg.jetchess.business.domain.state.StateEvent

sealed class SplashStateEvent : StateEvent {

    object GetUserData : SplashStateEvent() {
        override fun errorInfo(): String {
            return "Error getting user info"
        }

        override fun eventName(): String {
            return "GetUserData"
        }

        override fun shouldDisplayProgressBar() = false
    }
}