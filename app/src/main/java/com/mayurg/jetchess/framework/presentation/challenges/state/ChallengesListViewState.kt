package com.mayurg.jetchess.framework.presentation.challenges.state

import android.os.Parcelable
import com.mayurg.jetchess.business.domain.model.Challenge
import com.mayurg.jetchess.business.domain.state.ViewState
import kotlinx.parcelize.Parcelize

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */
@Parcelize
data class ChallengesListViewState(
    var list: List<Challenge>? = null
) : Parcelable, ViewState