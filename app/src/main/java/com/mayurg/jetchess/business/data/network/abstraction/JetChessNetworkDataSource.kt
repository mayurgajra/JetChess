package com.mayurg.jetchess.business.data.network.abstraction

import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO

interface JetChessNetworkDataSource {

    suspend fun registerUser(registerUserModel: RegisterUserModel): BaseResponseModel

    suspend fun loginUser(loginUserModel: LoginUserModel): BaseResponseModel

    suspend fun getUsers(): List<UserDTO>
}