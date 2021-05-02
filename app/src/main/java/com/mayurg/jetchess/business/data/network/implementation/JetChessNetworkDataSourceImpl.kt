package com.mayurg.jetchess.business.data.network.implementation

import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.mappers.NetworkMapper
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import javax.inject.Inject

class JetChessNetworkDataSourceImpl @Inject constructor(
    private val jetChessNetworkService: JetChessNetworkService,
    private val networkMapper: NetworkMapper
) : JetChessNetworkDataSource {
    override suspend fun registerUser(registerUserModel: RegisterUserModel) : BaseResponseModel {
      return  jetChessNetworkService.registerUser(registerUserModel)
    }
}