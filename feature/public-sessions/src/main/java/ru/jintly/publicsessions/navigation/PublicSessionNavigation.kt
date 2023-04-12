package ru.jintly.publicsessions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.publicsessions.presentation.PublicSessionsRoute

const val PUBLIC_SESSIONS_GRAPH = "public_sessions_graph"
private const val PUBLIC_SESSION_ROUTE = "public_session_route"

fun NavGraphBuilder.publicSessionsGraph(
    onToPlayerClick: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = PUBLIC_SESSIONS_GRAPH,
        startDestination = PUBLIC_SESSION_ROUTE,
    ) {
        composable(route = PUBLIC_SESSION_ROUTE) {
            PublicSessionsRoute(
                onToPlayerClick = onToPlayerClick,
            )
        }
        nestedGraphs()
    }
}
