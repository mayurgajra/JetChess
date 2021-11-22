package com.mayurg.jetchess.framework.datasource.network.implementation

import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.mappers.LoginNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.mappers.RegisterNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.model.ChallengeDTO
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO
import com.mayurg.jetchess.framework.datasource.network.retrofit.JetChessApiService

class JetChessNetworkServiceImpl(
    private val jetChessApiService: JetChessApiService,
    private val registerNetworkMapper: RegisterNetworkMapper,
    private val loginNetworkMapper: LoginNetworkMapper
) : JetChessNetworkService {

    override suspend fun registerUser(registerUser: RegisterUserModel): BaseResponseModel {
        val entity = registerNetworkMapper.mapToEntity(registerUser)
        return jetChessApiService.registerUser(entity)
    }

    override suspend fun loginUser(loginUserModel: LoginUserModel): BaseResponseModel {
        val entity = loginNetworkMapper.mapToEntity(loginUserModel)
        return jetChessApiService.loginUser(entity)
    }

    override suspend fun getUsers(): List<UserDTO> {
        return jetChessApiService.getUsers()
    }

    override suspend fun getChallenges(userId: String): List<ChallengeDTO> {
        return jetChessApiService.getChallenges(userId)
    }


}