package com.mayurg.jetchess.framework.presentation.login.state

import android.os.Parcelable
import com.mayurg.jetchess.business.domain.model.User
import com.mayurg.jetchess.business.domain.state.ViewState
import com.mayurg.jetchess.framework.datasource.network.model.LoginResponseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUserViewState(
    var loginResponseModel: LoginResponseModel? = null,
    var userDataSaved: User? = null
) : Parcelable, ViewState
