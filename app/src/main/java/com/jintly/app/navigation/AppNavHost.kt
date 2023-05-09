package com.jintly.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.jintly.feature.auth.navigation.AUTH_GRAPH
import ru.jintly.feature.auth.navigation.authGraph
import ru.jintly.feature.privatesessions.navigation.privateSessionsGraph
import ru.jintly.feature.profile.navigation.profileGraph
import ru.jintly.player.navigation.navigateToPlayer
import ru.jintly.player.navigation.playerScreen
import ru.jintly.publicsessions.navigation.navigateToPublicSessions
import ru.jintly.publicsessions.navigation.publicSessionsGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = AUTH_GRAPH,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph(
            navController = navController,
            onAuthSuccess = {
                navController.navigateToPublicSessions()
            },
        )
        publicSessionsGraph(
            onPublicSessionClick = { navController.navigateToPlayer() },
            nestedGraphs = {
                playerScreen()
            },
        )
        privateSessionsGraph(navController) { navController.navigateToPlayer() }
        profileGraph()
    }
}
