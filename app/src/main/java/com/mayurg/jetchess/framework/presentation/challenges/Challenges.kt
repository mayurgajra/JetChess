package com.mayurg.jetchess.framework.presentation.challenges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListStateEvent
import com.mayurg.jetchess.framework.presentation.challenges.state.ChallengesListStateEvent.AcceptRejectStateEvent
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            items(challengesListViewModel.viewState.value?.list?.size ?: 0) { pos ->
                ChallengesListItem(
                    challenge = challengesListViewModel.viewState.value?.list?.get(pos)!!,
                    onChallengeStatusChange = { id, status ->
                        challengesListViewModel.setStateEvent(AcceptRejectStateEvent(id, status))
                    }
                )
            }
        }
    }
}