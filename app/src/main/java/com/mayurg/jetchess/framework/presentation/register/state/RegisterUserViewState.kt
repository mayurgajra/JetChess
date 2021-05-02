package com.mayurg.jetchess.framework.presentation.register.state

import android.os.Parcelable
import com.mayurg.jetchess.business.domain.state.ViewState
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterUserViewState(
    var baseResponseModel: BaseResponseModel? = null
) : Parcelable, ViewState