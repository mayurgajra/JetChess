package com.mayurg.jetchess.business.domain.model

import java.io.Serializable

data class RegisterUserModel(
    val fullName: String? = null,
    val mobile: String? = null,
    val email: String,
    val password: String,
) : Serializable
