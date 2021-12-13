package com.mayurg.jetchess.business.data.local.abstraction

import com.mayurg.jetchess.business.domain.model.User

/**
 * Created On 12/12/2021
 * @author Mayur Gajra
 */
interface JetChessLocalDataSource {

    suspend fun saveUserInfo(user: User)

    suspend fun getUserInfo() : User?
}