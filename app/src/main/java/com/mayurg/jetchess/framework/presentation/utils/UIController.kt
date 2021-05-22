package com.mayurg.jetchess.framework.presentation.utils

import com.mayurg.jetchess.business.domain.state.Response
import com.mayurg.jetchess.business.domain.state.StateMessageCallback


interface UIController {

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

}


















