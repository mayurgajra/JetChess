package com.mayurg.jetchess.framework.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String) {

    sealed class MainScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens(
        route,
        title
    ) {
        object Home : MainScreens("home", "Home", Icons.Filled.Home)
        object Users : MainScreens("users", "Users", Icons.Filled.People)
        object Challenges : MainScreens("challenges", "Challenges", Icons.Filled.AddAlert)
        object Profile : MainScreens("profile", "Profile", Icons.Filled.Person)
    }
}

val screensInHomeFromBottomNav = listOf(
    Screens.MainScreens.Home,
    Screens.MainScreens.Users,
    Screens.MainScreens.Challenges,
    Screens.MainScreens.Profile
)

@Composable
fun Home(modifier: Modifier = Modifier, viewModel: MainViewModel, onClick: () -> Unit) {
    viewModel.setCurrentScreen(Screens.MainScreens.Home)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home", style = MaterialTheme.typography.h4)
        Button(
            onClick = onClick
        ) {
            Text("New Game")
        }
    }
}

@Composable
fun Users(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.MainScreens.Challenges)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Users.", style = MaterialTheme.typography.h4)
    }
}

@Composable
fun Challenges(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.MainScreens.Challenges)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Challenges.", style = MaterialTheme.typography.h4)
    }
}

@Composable
fun Profile(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.MainScreens.Profile)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile.", style = MaterialTheme.typography.h4)
    }
}