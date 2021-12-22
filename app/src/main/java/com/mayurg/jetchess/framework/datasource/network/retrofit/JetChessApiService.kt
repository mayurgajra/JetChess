package com.mayurg.jetchess.framework.datasource.network.retrofit

import com.mayurg.jetchess.business.domain.model.AcceptRejectChallengeRequest
import com.mayurg.jetchess.business.domain.model.CreateGameRoomRequest
import com.mayurg.jetchess.business.domain.model.SendChallengeRequest
import com.mayurg.jetchess.framework.datasource.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface JetChessApiService {

    @POST("register")
    suspend fun registerUser(@Body registerUserDTO: RegisterUserDTO): BaseResponseModel

    @POST("login")
    suspend fun loginUser(@Body loginUserDTO: LoginUserDTO): LoginResponseModel

    @GET("getUsers")
    suspend fun getUsers(@Query("loggedInUserId") userId: String): List<UserDTO>

    @GET("getChallenges")
    suspend fun getChallenges(@Query("userId") userId: String): List<ChallengeDTO>

    @POST("acceptRejectChallenge")
    suspend fun acceptRejectChallenge(@Body acceptRejectChallengeRequest: AcceptRejectChallengeRequest): BaseResponseModel

    @POST("createGameRoom")
    suspend fun createGameRoom(@Body createGameRoomRequest: CreateGameRoomRequest): BaseResponseModel

    @POST("sendChallenge")
    suspend fun sendChallenge(@Body request: SendChallengeRequest): SendChallengeResponse
}