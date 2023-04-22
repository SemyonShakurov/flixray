package ru.jintly.feature.privatesessions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.feature.privatesessions.PrivateSessionsRoute

const val PRIVATE_SESSIONS_GRAPH = "private_sessions_graph"
const val PRIVATE_SESSIONS_ROUTE = "private_sessions_route"

fun NavGraphBuilder.privateSessionsGraph() {
    navigation(
        route = PRIVATE_SESSIONS_GRAPH,
        startDestination = PRIVATE_SESSIONS_ROUTE,
    ) {
        composable(
            route = PRIVATE_SESSIONS_ROUTE,
        ) {
            PrivateSessionsRoute()
        }
    }
}
