package com.mayurg.jetchess.framework.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mayurg.jetchess.framework.presentation.base.BaseActivity


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppScaffold()
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
            if (currentScreen == Screens.HomeScreens.Favorite || currentScreen is Screens.HomeScreens) {
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
            startDestination = Screens.HomeScreens.Favorite.route
        ) {
            composable(Screens.HomeScreens.Favorite.route) { Favorite(viewModel = viewModel) }
            composable(Screens.HomeScreens.Notification.route) { Notification(viewModel = viewModel) }
            composable(Screens.HomeScreens.MyNetwork.route) { MyNetwork(viewModel = viewModel) }
        }
    }

    @Composable
    fun BottomBar(
        modifier: Modifier = Modifier,
        screens: List<Screens.HomeScreens>,
        navController: NavController
    ) {
        BottomNavigation(modifier = modifier) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString("android-support-nav:controller:route")
            screens.forEach { screen ->
                BottomNavigationItem(
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