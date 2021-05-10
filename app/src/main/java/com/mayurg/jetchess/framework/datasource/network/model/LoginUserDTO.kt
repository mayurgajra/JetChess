package com.mayurg.jetchess.framework.datasource.network.model

import java.io.Serializable

data class LoginUserDTO(
    val email: String,
    val password: String,
) : Serializable
