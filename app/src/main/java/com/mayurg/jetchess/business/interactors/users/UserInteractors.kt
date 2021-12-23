package com.mayurg.jetchess.business.interactors.users

import com.mayurg.jetchess.business.interactors.users.register.GetUserInfo
import com.mayurg.jetchess.business.interactors.users.register.LoginUser
import com.mayurg.jetchess.business.interactors.users.register.RegisterUser
import com.mayurg.jetchess.business.interactors.users.register.SaveUserInfo
import com.mayurg.jetchess.business.interactors.users.userlist.CreateGameRoom
import com.mayurg.jetchess.business.interactors.users.userlist.SendChallenge
import com.mayurg.jetchess.business.interactors.users.userlist.UsersList
import javax.inject.Inject

class UserInteractors @Inject constructor(
    val registerUser: RegisterUser,
    val loginUser: LoginUser,
    val usersList: UsersList,
    val sendChallenge: SendChallenge,
    val createGameRoom: CreateGameRoom,
    val saveUserInfo: SaveUserInfo,
    val getUserInfo: GetUserInfo
)