package com.mayurg.jetchess.business.domain.state

import android.view.View
import com.mayurg.jetchess.util.TodoCallback


data class StateMessage(val response: Response)

data class Response(
    val message: Enum<*>?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType{

    class Toast: UIComponentType()

    class Dialog: UIComponentType()

    class None: UIComponentType()
}

sealed class MessageType{

    class Success: MessageType()

    class Error: MessageType()

    class Info: MessageType()

    class None: MessageType()
}


interface StateMessageCallback{

    fun removeMessageFromStack()
}


interface AreYouSureCallback {

    fun proceed()

    fun cancel()
}

interface SnackbarUndoCallback {

    fun undo()
}

class SnackbarUndoListener
    constructor(
        private val snackbarUndoCallback: SnackbarUndoCallback?
    ): View.OnClickListener {

    override fun onClick(v: View?) {
        snackbarUndoCallback?.undo()
    }

}


interface DialogInputCaptureCallback {

    fun onTextCaptured(text: String)
}















