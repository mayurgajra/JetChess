package com.mayurg.jetchess.framework.presentation.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
fun Users(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    mainViewModel.setCurrentScreen(Screens.MainScreens.Challenges)

    val viewModel: UsersListViewModel = hiltViewModel<UsersListViewModel>()
    viewModel.setStateEvent(UsersListStateEvent.GetUsersEvent)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Users.", style = MaterialTheme.typography.h4)
    }
}