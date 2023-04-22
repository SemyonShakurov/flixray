package com.jintly.app.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jintly.app.R
import com.jintly.app.navigation.AppNavHost
import com.jintly.app.navigation.TopLevelScreen
import ru.jintly.core.data.utils.NetworkMonitor
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.colors.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    networkMonitor: NetworkMonitor,
) {
    val appState = rememberAppState(networkMonitor = networkMonitor)
    val navController = appState.navController

    // connection checks
    val snackbarHostState = remember { SnackbarHostState() }
    val isOnline by appState.isOnline.collectAsStateWithLifecycle()
    val notConnectedMessage = stringResource(R.string.network_error)
    LaunchedEffect(isOnline) {
        if (!isOnline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        }
    }

    val topLevelScreens = listOf(
        TopLevelScreen.PublicSessions,
        TopLevelScreen.PrivateSessions,
        TopLevelScreen.Profile,
    )
    val isBottomBarVisible =
        navController.currentBackStackEntryAsState().value?.destination?.route in topLevelScreens.map { it.route }
    val isTopBarVisible =
        navController.currentBackStackEntryAsState().value?.destination?.route in topLevelScreens.map { it.route }

    var topTitle by remember { mutableStateOf("") }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            topTitle = when {
                backStackEntry.destination.route.toString() == TopLevelScreen.PublicSessions.route -> "Главная"
                backStackEntry.destination.route.toString() == TopLevelScreen.PrivateSessions.route -> "Комнаты"
                backStackEntry.destination.route.toString() == TopLevelScreen.Profile.route -> "Профиль"
                else -> ""
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (isTopBarVisible) {
                TopAppBar(
                    title = { Text(text = topTitle, color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Secondary,
                    ),
                )
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                NavigationBar(
                    containerColor = Secondary,
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    topLevelScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    modifier = Modifier.height(24.dp),
                                    painter = painterResource(id = screen.icon.id),
                                    contentDescription = null,
                                    tint = Primary,
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(id = screen.titleId),
                                    color = Primary,
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Secondary,
                            ),
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        AppNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )
    }
}
