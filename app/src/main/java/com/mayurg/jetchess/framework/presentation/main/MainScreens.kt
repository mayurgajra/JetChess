package com.mayurg.jetchess.framework.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class Screens(val route: String, val title: String) {

    sealed class MainScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens(
        route,
        title
    ) {
        object Users : MainScreens("users", "Users", Icons.Filled.People)
        object Challenges : MainScreens("challenges", "Challenges", Icons.Filled.AddAlert)
        object Profile : MainScreens("profile", "Profile", Icons.Filled.Person)
    }
}

val screensInHomeFromBottomNav = listOf(
    Screens.MainScreens.Users,
    Screens.MainScreens.Challenges,
    Screens.MainScreens.Profile
)


@Composable
fun Profile(modifier: Modifier = Modifier, viewModel: MainViewModel, onLogoutClicked: () -> Unit) {
    viewModel.setCurrentScreen(Screens.MainScreens.Profile)
    val userName = viewModel.userName.observeAsState("")

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello, ${userName.value}", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onLogoutClicked) {
            Text(text = "Logout")
        }
    }
}
