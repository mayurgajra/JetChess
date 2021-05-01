package com.mayurg.jetchess.framework.datasource.network.abstraction

import com.mayurg.jetchess.framework.datasource.network.model.RegisterUserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface JetChessApiService {

    @POST("register")
    suspend fun registerUser(@Body registerUserModel: RegisterUserModel)
}