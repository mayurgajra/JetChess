package com.mayurg.jetchess.framework.datasource.network.model

import com.mayurg.jetchess.business.domain.model.User
import java.io.Serializable

data class LoginResponseModel(
    val successful: Boolean,
    val message: String,
    val user: User? = null
) : Serializable