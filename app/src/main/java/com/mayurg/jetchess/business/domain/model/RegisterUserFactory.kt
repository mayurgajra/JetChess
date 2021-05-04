package com.mayurg.jetchess.business.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterUserFactory @Inject constructor() {

    fun createUser(
        fullName: String,
        mobile: String,
        email: String,
        password: String,
    ): RegisterUserModel {
        return RegisterUserModel(fullName, mobile, email, password)
    }
}