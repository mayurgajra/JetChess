package com.mayurg.jetchess.framework.presentation.challenges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListStateEvent
import com.mayurg.jetchess.framework.presentation.main.MainViewModel
import com.mayurg.jetchess.framework.presentation.main.Screens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created On 22/11/2021
 * @author Mayur Gajra
 */

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Challenges(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    challengesListViewModel: ChallengesListViewModel
) {
    mainViewModel.setCurrentScreen(Screens.MainScreens.Challenges)
    challengesListViewModel.setStateEvent(ChallengesListStateEvent.GetChallengesStateEvent)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Challenges.", style = MaterialTheme.typography.h4)
    }
}