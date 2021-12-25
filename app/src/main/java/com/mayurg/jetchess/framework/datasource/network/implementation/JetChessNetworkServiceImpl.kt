package com.mayurg.jetchess.framework.datasource.network.implementation

import com.mayurg.jetchess.business.domain.model.*
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.mappers.LoginNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.mappers.RegisterNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.model.*
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

    override suspend fun loginUser(loginUserModel: LoginUserModel): LoginResponseModel {
        val entity = loginNetworkMapper.mapToEntity(loginUserModel)
        return jetChessApiService.loginUser(entity)
    }

    override suspend fun getUsers(loggedInUserId: String): List<UserDTO> {
        return jetChessApiService.getUsers(loggedInUserId)
    }

    override suspend fun getChallenges(userId: String): List<ChallengeDTO> {
        return jetChessApiService.getChallenges(userId)
    }

    override suspend fun acceptRejectChallenge(id: String, status: String): BaseResponseModel {
        return jetChessApiService.acceptRejectChallenge(AcceptRejectChallengeRequest(id, status))
    }

    override suspend fun createGameRoom(id: String): BaseResponseModel {
        return jetChessApiService.createGameRoom(CreateGameRoomRequest(roomId = id))
    }

    override suspend fun joinGameRoom(userId: String, roomId: String): BaseResponseModel {
        return jetChessApiService.joinGameRoom(userId, roomId)
    }

    override suspend fun sendChallenge(fromId: String, toId: String): SendChallengeResponse {
        return jetChessApiService.sendChallenge(SendChallengeRequest(fromId = fromId, toId = toId))
    }
}