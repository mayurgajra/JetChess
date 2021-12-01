package com.mayurg.jetchess.business.interactors.challenges

import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.framework.datasource.network.model.BaseResponseModel
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListStateEvent
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 01/12/2021
 * @author Mayur Gajra
 */
class AcceptRejectChallenge @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
) {

    fun acceptRejectChallenge(stateEvent: ChallengesListStateEvent.AcceptRejectStateEvent): Flow<DataState<ChallengesListViewState>?> =
        flow {
            val apiResult = safeApiCall(Dispatchers.IO) {
                jetChessNetworkDataSource.acceptRejectChallenge(stateEvent.id, stateEvent.status)
            }

            val response = object : ApiResponseHandler<ChallengesListViewState, BaseResponseModel>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: BaseResponseModel): DataState<ChallengesListViewState>? {
                    return DataState.data(
                        response = null,
                        data = ChallengesListViewState(statusChangeResult = resultObj),
                        stateEvent = null
                    )
                }

            }.getResult()

            emit(response)
        }

}