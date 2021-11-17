package com.mayurg.jetchess.framework.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.playgame.PlayGameActivity
import com.mayurg.jetchess.framework.presentation.users.Users
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AppScaffold()
            }
        }

    }

    @Composable
    fun AppScaffold() {
        val viewModel: MainViewModel = viewModel()
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val currentScreen by viewModel.currentScreen.observeAsState()

        val bottomBar: @Composable () -> Unit = {
            if (currentScreen == Screens.MainScreens.Home || currentScreen is Screens.MainScreens) {
                BottomBar(
                    navController = navController,
                    screens = screensInHomeFromBottomNav
                )
            }
        }

        Scaffold(
            bottomBar = {
                bottomBar()
            },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        ) { innerPadding ->
            NavigationHost(navController = navController, viewModel = viewModel)
        }
    }

    @Composable
    fun NavigationHost(navController: NavController, viewModel: MainViewModel) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = Screens.MainScreens.Home.route
        ) {
            composable(Screens.MainScreens.Home.route) {
                Home(viewModel = viewModel, onClick = {
                    moveToGame()
                })
            }
            composable(Screens.MainScreens.Users.route) { Users(mainViewModel = viewModel) }
            composable(Screens.MainScreens.Challenges.route) { Challenges(viewModel = viewModel) }
            composable(Screens.MainScreens.Profile.route) { Profile(viewModel = viewModel) }
        }
    }

    private fun moveToGame() {
        val intent = Intent(this, PlayGameActivity::class.java)
        startActivity(intent)
    }

    @Composable
    fun BottomBar(
        modifier: Modifier = Modifier,
        screens: List<Screens.MainScreens>,
        navController: NavController
    ) {
        BottomNavigation(
            modifier = modifier.background(Color.Black)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screens.forEach { screen ->
                BottomNavigationItem(
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = Color.Gray,
                    icon = { Icon(imageVector = screen.icon, contentDescription = "") },
                    label = { Text(screen.title) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}