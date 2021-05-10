package com.mayurg.jetchess.business.domain.model

import java.io.Serializable

data class LoginUserModel(
    val email: String,
    val password: String,
) : Serializable
