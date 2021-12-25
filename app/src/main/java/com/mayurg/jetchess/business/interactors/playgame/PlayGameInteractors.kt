package com.mayurg.jetchess.business.interactors.playgame

import javax.inject.Inject

/**
 * Created On 25/12/2021
 * @author Mayur Gajra
 */
class PlayGameInteractors @Inject constructor(
    val joinGameRoom: JoinGameRoom
)