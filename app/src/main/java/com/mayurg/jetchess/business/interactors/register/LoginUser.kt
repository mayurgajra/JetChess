package com.mayurg.jetchess.business.interactors.register

import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.model.LoginUserFactory
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.presentation.login.state.LoginStateEvent
import com.mayurg.jetchess.framework.presentation.login.state.LoginUserViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
    private val loginUserFactory: LoginUserFactory
)  {

    fun loginUser(
        email: String,
        password: String,
        stateEvent: StateEvent
    ): Flow<DataState<LoginUserViewState>?> = flow {
        val user = loginUserFactory.createUser(email, password)

        val callResult = safeApiCall(Dispatchers.IO) {
            jetChessNetworkDataSource.loginUser(user)
        }

        val response = object : ApiResponseHandler<LoginUserViewState, BaseResponseModel>(
            response = callResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: BaseResponseModel): DataState<LoginUserViewState>? {
                return DataState.data(
                    response = null,
                    data = LoginUserViewState(resultObj),
                    stateEvent = null
                )
            }

        }.getResult()

        emit(response)
    }


}