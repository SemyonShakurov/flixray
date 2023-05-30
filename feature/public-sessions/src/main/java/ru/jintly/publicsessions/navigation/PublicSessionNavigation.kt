package ru.jintly.publicsessions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.publicsessions.presentation.PublicSessionsRoute

const val PUBLIC_SESSIONS_GRAPH = "public_sessions_graph"
const val PUBLIC_SESSION_ROUTE = "public_session_route"

fun NavController.navigateToPublicSessions() {
    this.navigate(route = PUBLIC_SESSION_ROUTE)
}

fun NavGraphBuilder.publicSessionsGraph(
    onPublicSessionClick: (String) -> Unit,
    onPrivateSessionClick: (String, Boolean) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = PUBLIC_SESSIONS_GRAPH,
        startDestination = PUBLIC_SESSION_ROUTE,
    ) {
        composable(route = PUBLIC_SESSION_ROUTE) {
            PublicSessionsRoute(
                onPublicSessionClick = onPublicSessionClick,
                onPrivateSessionClick = onPrivateSessionClick,
            )
        }
        nestedGraphs()
    }
}
