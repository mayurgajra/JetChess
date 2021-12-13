package com.mayurg.jetchess.framework.presentation.splash.state

import android.os.Parcelable
import com.mayurg.jetchess.business.domain.model.User
import com.mayurg.jetchess.business.domain.state.ViewState
import kotlinx.parcelize.Parcelize

/**
 * Created On 13/12/2021
 * @author Mayur Gajra1
 */
@Parcelize
data class SplashViewState(
    var userDataSaved: User? = null
) : Parcelable, ViewState