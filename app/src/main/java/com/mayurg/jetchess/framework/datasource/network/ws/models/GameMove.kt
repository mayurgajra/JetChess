package com.mayurg.jetchess.framework.datasource.network.ws.models

import com.mayurg.jetchess.framework.presentation.playgame.gameview.Move
import com.mayurg.jetchess.util.Constants.TYPE_MOVE

data class GameMove(
    val userId: String,
    val roomId: String,
    val move: Move,
): BaseModel(TYPE_MOVE)