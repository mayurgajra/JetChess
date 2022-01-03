package com.mayurg.jetchess.framework.presentation.playgame.state

import java.io.Serializable

/**
 * Created On 25/12/2021
 * @author Mayur Gajra
 */
data class PlayGameViewState(
    var hasJoinedGameRoom: Boolean? = false
) : Serializable
