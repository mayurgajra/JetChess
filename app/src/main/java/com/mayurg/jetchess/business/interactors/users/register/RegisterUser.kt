package com.mayurg.jetchess.business.interactors.users.register

import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.model.RegisterUserFactory
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.presentation.register.state.RegisterUserViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
    private val registerUserFactory: RegisterUserFactory
) {

    fun registerUser(
        fullName: String,
        mobile: String,
        email: String,
        password: String,
        stateEvent: StateEvent,
    ): Flow<DataState<RegisterUserViewState>?> = flow {
        val user = registerUserFactory.createUser(fullName, mobile, email, password)

        val callResult = safeApiCall(IO) {
            jetChessNetworkDataSource.registerUser(user)
        }

        val response = object : ApiResponseHandler<RegisterUserViewState, BaseResponseModel>(
            response = callResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: BaseResponseModel): DataState<RegisterUserViewState>? {
                return DataState.data(
                    response = null,
                    data = RegisterUserViewState(resultObj),
                    stateEvent = null
                )
            }

        }.getResult()

        emit(response)
    }
}