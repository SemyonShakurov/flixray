package com.jintly.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.jintly.player.navigation.navigateToPlayer
import ru.jintly.player.navigation.playerScreen
import ru.jintly.publicsessions.navigation.PUBLIC_SESSIONS_GRAPH
import ru.jintly.publicsessions.navigation.publicSessionsGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = PUBLIC_SESSIONS_GRAPH,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        publicSessionsGraph(
            onToPlayerClick = {
                navController.navigateToPlayer()
            },
            nestedGraphs = {
                playerScreen()
            },
        )
    }
}
