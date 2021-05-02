package com.mayurg.jetchess.framework.datasource.network.retrofit

import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.datasource.network.model.RegisterUserDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface JetChessApiService {

    @POST("register")
    suspend fun registerUser(@Body registerUserDTO: RegisterUserDTO): BaseResponseModel
}