package com.mayurg.jetchess.business.data.local.implementation

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.domain.model.User

/**
 * Created On 12/12/2021
 * @author Mayur Gajra
 */
class JetChessLocalDataSourceImpl constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) : JetChessLocalDataSource {

    companion object {
        private const val USER_INFO = "user_info"
    }

    override suspend fun saveUserInfo(user: User) {
        prefs.edit().putString(USER_INFO, gson.toJson(user)).apply()
    }


}