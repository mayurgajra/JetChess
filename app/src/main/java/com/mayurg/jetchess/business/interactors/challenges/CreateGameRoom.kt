package com.mayurg.jetchess.business.interactors.challenges

import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import javax.inject.Inject

/**
 * Created On 21/12/2021
 * @author Mayur Gajra
 */
class CreateGameRoom @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
) {

    fun createGameRoom() {

    }
}