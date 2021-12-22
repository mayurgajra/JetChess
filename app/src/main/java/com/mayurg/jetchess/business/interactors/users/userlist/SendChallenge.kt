package com.mayurg.jetchess.business.interactors.users.userlist

import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.datasource.network.model.SendChallengeResponse
import com.mayurg.jetchess.framework.presentation.users.state.UserListViewState
import com.mayurg.jetchess.framework.presentation.users.state.UsersListStateEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 22/12/2021
 * @author Mayur Gajra
 */
class SendChallenge @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
    private val jetChessLocalDataSource: JetChessLocalDataSource
) {

    fun sendChallenge(stateEvent: UsersListStateEvent.SendChallengeEvent): Flow<DataState<UserListViewState>?> =
        flow {
            val user = jetChessLocalDataSource.getUserInfo()

            if (user == null) {
                emit(
                    DataState.data(
                        response = null,
                        data = UserListViewState(),
                        stateEvent = null
                    )
                )
                return@flow
            }

            val callResult = safeApiCall(Dispatchers.IO) {
                jetChessNetworkDataSource.sendChallenge(fromId = user.id, toId = stateEvent.toId)
            }

            val response = object : ApiResponseHandler<UserListViewState, SendChallengeResponse>(
                response = callResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: SendChallengeResponse): DataState<UserListViewState>? {
                    return DataState.data(
                        response = null,
                        data = UserListViewState(sendChallengeResponse = resultObj),
                        stateEvent = null
                    )
                }

            }.getResult()

            emit(response)

        }
}