package com.mayurg.jetchess.framework.datasource.network.abstraction

import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO

interface JetChessNetworkService {

    suspend fun registerUser(registerUser: RegisterUserModel): BaseResponseModel

    suspend fun loginUser(loginUserModel: LoginUserModel): BaseResponseModel

    suspend fun getUsers(): List<UserDTO>
}