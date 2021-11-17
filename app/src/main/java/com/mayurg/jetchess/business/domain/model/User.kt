package com.mayurg.jetchess.business.domain.model

import java.io.Serializable

data class User(
    val id: String,
    val fullName: String,
    val mobile: String,
    val email: String
) : Serializable