package com.mayurg.jetchess.business.interactors.users.register

import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.presentation.splash.state.SplashViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 12/12/2021
 * @author Mayur Gajra
 */
class GetUserInfo @Inject constructor(
    private val jetChessLocalDataSource: JetChessLocalDataSource
) {

    fun getUserInfo(): Flow<DataState<SplashViewState>?> = flow {

        val user = jetChessLocalDataSource.getUserInfo()

        val response = DataState.data(
            response = null,
            data = SplashViewState(userDataSaved = user),
            stateEvent = null
        )

        emit(response)
    }
}