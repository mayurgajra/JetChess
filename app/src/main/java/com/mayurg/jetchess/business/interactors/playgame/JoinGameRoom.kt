package com.mayurg.jetchess.business.interactors.playgame

import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameStateEvent
import com.mayurg.jetchess.framework.presentation.playgame.state.PlayGameViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 25/12/2021
 * @author Mayur Gajra
 */
class JoinGameRoom @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
    private val jetChessLocalDataSource: JetChessLocalDataSource
) {

    fun joinGameRoom(stateEvent: PlayGameStateEvent.JoinRoomEvent): Flow<DataState<PlayGameViewState>?> =
        flow {

            val user = jetChessLocalDataSource.getUserInfo()

            if (user == null) {
                emit(
                    DataState.data(
                        response = null,
                        data = PlayGameViewState(),
                        stateEvent = null
                    )
                )
                return@flow
            }

            val callResult = safeApiCall(Dispatchers.IO) {
                jetChessNetworkDataSource.joinGameRoom(userId = user.id, roomId = stateEvent.roomId)
            }

            val response = object : ApiResponseHandler<PlayGameViewState, BaseResponseModel>(
                response = callResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: BaseResponseModel): DataState<PlayGameViewState>? {
                    return DataState.data(
                        response = null,
                        data = PlayGameViewState(
                            hasJoinedGameRoom = resultObj.successful
                        ),
                        stateEvent = null
                    )
                }

            }.getResult()

            emit(response)

        }
}