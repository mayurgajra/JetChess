package com.mayurg.jetchess.business.interactors.users.register

import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.domain.model.User
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.presentation.login.state.LoginUserViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 12/12/2021
 * @author Mayur Gajra
 */
class SaveUserInfo @Inject constructor(
    private val jetChessLocalDataSource: JetChessLocalDataSource
) {

    fun saveUserInfo(user: User): Flow<DataState<LoginUserViewState>?> = flow {

        jetChessLocalDataSource.saveUserInfo(user)

        val response = DataState.data(
            response = null,
            data = LoginUserViewState(userDataSaved = user),
            stateEvent = null
        )

        emit(response)
    }
}