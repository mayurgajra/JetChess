package com.mayurg.jetchess.framework.presentation.users.state

import android.os.Parcelable
import com.mayurg.jetchess.business.domain.model.User
import com.mayurg.jetchess.business.domain.state.ViewState
import com.mayurg.jetchess.framework.datasource.network.model.SendChallengeResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserListViewState(
    var list: List<User>? = null,
    var sendChallengeResponse: SendChallengeResponse? = null,
    var roomId: String? = null
) : Parcelable, ViewState