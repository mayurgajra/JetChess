package com.mayurg.jetchess.business.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUserFactory @Inject constructor() {

    fun createUser(
        email: String,
        password: String
    ): LoginUserModel {
        return LoginUserModel(email, password)
    }
}