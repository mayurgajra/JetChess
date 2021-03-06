package com.mayurg.jetchess.business.interactors.challenges

import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.framework.datasource.network.mappers.ChallengesNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.model.ChallengeDTO
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */
class ChallengesList @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
    private val challengesNetworkMapper: ChallengesNetworkMapper,
    private val jetChessLocalDataSource: JetChessLocalDataSource
) {


    fun getChallengesList(stateEvent: StateEvent): Flow<DataState<ChallengesListViewState>?> =
        flow {

            val user = jetChessLocalDataSource.getUserInfo()

            if (user == null) {
                emit(
                    DataState.data(
                        response = null,
                        data = ChallengesListViewState(),
                        stateEvent = null
                    )
                )
                return@flow
            }

            val callResult = safeApiCall(Dispatchers.IO) {
                jetChessNetworkDataSource.getChallenges(userId = user.id)
            }

            val response = object : ApiResponseHandler<ChallengesListViewState, List<ChallengeDTO>>(
                response = callResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<ChallengeDTO>): DataState<ChallengesListViewState>? {
                    return DataState.data(
                        response = null,
                        data = ChallengesListViewState(resultObj.map {
                            challengesNetworkMapper.mapFromEntity(
                                it
                            )
                        }),
                        stateEvent = null
                    )
                }

            }.getResult()

            emit(response)
        }

}