package com.mayurg.jetchess.framework.datasource.network.retrofit

import com.mayurg.jetchess.framework.datasource.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface JetChessApiService {

    @POST("register")
    suspend fun registerUser(@Body registerUserDTO: RegisterUserDTO): BaseResponseModel

    @POST("login")
    suspend fun loginUser(@Body loginUserDTO: LoginUserDTO): BaseResponseModel

    @GET("getUsers")
    suspend fun getUsers(): List<UserDTO>

    @GET("getChallenges")
    suspend fun getChallenges(@Query("userId") userId: String): List<ChallengeDTO>
}