package com.mayurg.jetchess.business.data.network.implementation

import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.model.ChallengeDTO
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO
import javax.inject.Inject

class JetChessNetworkDataSourceImpl @Inject constructor(
    private val jetChessNetworkService: JetChessNetworkService
) : JetChessNetworkDataSource {

    override suspend fun registerUser(registerUserModel: RegisterUserModel): BaseResponseModel {
        return jetChessNetworkService.registerUser(registerUserModel)
    }

    override suspend fun loginUser(loginUserModel: LoginUserModel): BaseResponseModel {
        return jetChessNetworkService.loginUser(loginUserModel)
    }

    override suspend fun getUsers(): List<UserDTO> {
        return jetChessNetworkService.getUsers()
    }

    override suspend fun getChallenges(userId: String): List<ChallengeDTO> {
        return jetChessNetworkService.getChallenges(userId)
    }


}