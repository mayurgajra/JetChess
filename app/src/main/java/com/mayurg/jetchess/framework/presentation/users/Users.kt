package com.mayurg.jetchess.framework.presentation.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.framework.presentation.main.MainViewModel
import com.mayurg.jetchess.framework.presentation.main.Screens
import com.mayurg.jetchess.framework.presentation.users.state.UsersListStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created On 17/11/2021
 * @author Mayur Gajra
 */

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Users(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    usersListViewModel: UsersListViewModel
) {
    mainViewModel.setCurrentScreen(Screens.MainScreens.Users)
    usersListViewModel.setStateEvent(UsersListStateEvent.GetUsersEvent)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            items(usersListViewModel.viewState.value?.list?.size ?: 0) { pos ->
                UsersListItem(
                    user = usersListViewModel.viewState.value?.list?.get(pos)!!
                )
            }
        }
    }
}