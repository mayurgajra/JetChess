package com.mayurg.jetchess.framework.datasource.network.abstraction

import com.mayurg.jetchess.business.domain.model.LoginUserModel
import com.mayurg.jetchess.business.domain.model.RegisterUserModel
import com.mayurg.jetchess.framework.datasource.network.model.*

interface JetChessNetworkService {

    suspend fun registerUser(registerUser: RegisterUserModel): BaseResponseModel

    suspend fun loginUser(loginUserModel: LoginUserModel): LoginResponseModel

    suspend fun getUsers(loggedInUserId: String): List<UserDTO>

    suspend fun getChallenges(userId: String): List<ChallengeDTO>

    suspend fun acceptRejectChallenge(id: String, status: String): BaseResponseModel

    suspend fun createGameRoom(id: String): BaseResponseModel

    suspend fun sendChallenge(fromId: String, toId: String): SendChallengeResponse

    suspend fun joinGameRoom(userId: String, roomId: String): BaseResponseModel
}