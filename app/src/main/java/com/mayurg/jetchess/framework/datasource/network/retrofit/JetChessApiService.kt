package com.mayurg.jetchess.framework.datasource.network.retrofit

import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.model.LoginUserDTO
import com.mayurg.jetchess.framework.datasource.network.model.RegisterUserDTO
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JetChessApiService {

    @POST("register")
    suspend fun registerUser(@Body registerUserDTO: RegisterUserDTO): BaseResponseModel

    @POST("login")
    suspend fun loginUser(@Body loginUserDTO: LoginUserDTO): BaseResponseModel

    @GET("getUsers")
    suspend fun getUsers(): List<UserDTO>
}