package com.mayurg.jetchess.framework.datasource.network.ws.models

import com.mayurg.jetchess.util.Constants.TYPE_PLAYER_FE


data class PlayerFE(
    val playerName: String = "",
    val playerId: String = ""
) : BaseModel(TYPE_PLAYER_FE)