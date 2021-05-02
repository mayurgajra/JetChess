package com.mayurg.jetchess.business.data.network.abstraction

import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel

interface JetChessNetworkDataSource {

    suspend fun registerUser(registerUserModel: RegisterUserModel): BaseResponseModel
}