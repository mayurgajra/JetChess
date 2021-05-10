package com.mayurg.jetchess.framework.datasource.network.implementation

import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.retrofit.JetChessApiService

class JetChessNetworkServiceImpl(
    private val jetChessApiService: JetChessApiService,
    private val networkMapper: NetworkMapper
) : JetChessNetworkService {

    override suspend fun registerUser(registerUser: RegisterUserModel): BaseResponseModel {
        val entity = networkMapper.mapToEntity(registerUser)
        return jetChessApiService.registerUser(entity)
    }

    override suspend fun loginUser(loginUserModel: LoginUserModel): BaseResponseModel {
//        val entity = networkMapper.mapToEntity(registerUser)
        return jetChessApiService.registerUser(entity)
    }


}