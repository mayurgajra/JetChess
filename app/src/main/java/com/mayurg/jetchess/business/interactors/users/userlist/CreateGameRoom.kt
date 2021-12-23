package com.mayurg.jetchess.business.interactors.users.userlist

import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.presentation.users.state.UserListViewState
import com.mayurg.jetchess.framework.presentation.users.state.UsersListStateEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 23/12/2021
 * @author Mayur Gajra
 */
class CreateGameRoom @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
) {

    fun createGameRoom(stateEvent: UsersListStateEvent.CreateGameRoomEvent): Flow<DataState<UserListViewState>?> =
        flow {

            val callResult = safeApiCall(Dispatchers.IO) {
                jetChessNetworkDataSource.createGameRoom(id = stateEvent.roomId)
            }

            val response = object : ApiResponseHandler<UserListViewState, BaseResponseModel>(
                response = callResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: BaseResponseModel): DataState<UserListViewState>? {
                    return DataState.data(
                        response = null,
                        data = UserListViewState(
                            roomId =
                            if (resultObj.successful) stateEvent.roomId else null
                        ),
                        stateEvent = null
                    )
                }

            }.getResult()

            emit(response)

        }
}