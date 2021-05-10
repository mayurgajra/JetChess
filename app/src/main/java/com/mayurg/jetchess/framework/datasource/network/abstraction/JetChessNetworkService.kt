package com.mayurg.jetchess.framework.datasource.network.abstraction

import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel

interface JetChessNetworkService {

    suspend fun registerUser(registerUser: RegisterUserModel): BaseResponseModel

    suspend fun loginUser(loginUserModel: LoginUserModel): BaseResponseModel
}