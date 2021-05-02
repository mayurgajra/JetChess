package com.mayurg.jetchess.business.data.network.abstraction

import com.mayurg.jetchess.business.domain.model.RegisterUserModel

interface JetChessNetworkDataSource {

    suspend fun registerUser(registerUserModel: RegisterUserModel)
}