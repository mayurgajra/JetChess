package com.mayurg.jetchess.framework.presentation.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.mayurg.jetchess.R
import com.mayurg.jetchess.business.domain.state.Response
import com.mayurg.jetchess.business.domain.state.SnackbarUndoCallback
import com.mayurg.jetchess.business.domain.state.StateMessageCallback
import com.mayurg.jetchess.business.domain.state.UIComponentType
import com.mayurg.jetchess.framework.presentation.utils.UIController
import com.mayurg.jetchess.util.TodoCallback

abstract class BaseActivity : AppCompatActivity(), UIController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    ) {
        when (response.uiComponentType) {

            is UIComponentType.Toast -> {
                response.message?.let {
                    displayToast(
                        message = it.toString(),
                        stateMessageCallback = stateMessageCallback
                    )
                }
            }

            is UIComponentType.None -> {
                // This would be a good place to send to your Error Reporting
                // software of choice (ex: Firebase crash reporting)
                Log.i("MG-JetChess", "onResponseReceived: ${response.message}")
                stateMessageCallback.removeMessageFromStack()
            }
        }
    }



    fun displayToast(
        message: String,
        stateMessageCallback: StateMessageCallback
    ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        stateMessageCallback.removeMessageFromStack()
    }
}