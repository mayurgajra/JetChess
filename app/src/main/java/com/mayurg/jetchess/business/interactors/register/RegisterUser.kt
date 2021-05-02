package com.mayurg.jetchess.business.interactors.register

import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.domain.model.RegisterUserFactory
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.presentation.register.state.RegisterStateEvent
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
    ): Flow<DataState<RegisterStateEvent>?> = flow {
        val user = registerUserFactory.createUser(fullName, mobile, email, password)
        jetChessNetworkDataSource.registerUser(user)
    }
}