package com.mayurg.jetchess.business.interactors

import com.mayurg.jetchess.business.interactors.register.RegisterUser
import javax.inject.Inject

class UserInteractors @Inject constructor(
    val registerUser: RegisterUser
)