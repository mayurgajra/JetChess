package com.mayurg.jetchess.business.interactors.users

import com.mayurg.jetchess.business.data.network.ApiResponseHandler
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.util.safeApiCall
import com.mayurg.jetchess.business.domain.state.DataState
import com.mayurg.jetchess.business.domain.state.StateEvent
import com.mayurg.jetchess.framework.datasource.network.mappers.UserNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.model.UserDTO
import com.mayurg.jetchess.framework.presentation.users.state.UserListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created On 17/11/2021
 * @author Mayur Gajra
 */
class UsersList @Inject constructor(
    private val jetChessNetworkDataSource: JetChessNetworkDataSource,
    private val userNetworkMapper: UserNetworkMapper
) {

    fun getUsers(stateEvent: StateEvent): Flow<DataState<UserListViewState>?> = flow {
        val callResult = safeApiCall(Dispatchers.IO) {
            jetChessNetworkDataSource.getUsers()
        }

        val response = object : ApiResponseHandler<UserListViewState, List<UserDTO>>(
            response = callResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<UserDTO>): DataState<UserListViewState>? {
                return DataState.data(
                    response = null,
                    data = UserListViewState(resultObj.map { userNetworkMapper.mapFromEntity(it) }),
                    stateEvent = null
                )
            }

        }.getResult()

        emit(response)
    }
}