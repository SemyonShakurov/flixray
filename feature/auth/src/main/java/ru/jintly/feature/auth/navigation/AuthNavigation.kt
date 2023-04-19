package ru.jintly.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.feature.auth.AuthMainRoute

const val AUTH_GRAPH = "auth_graph"
private const val AUTH_ROUTE = "auth_route"

fun NavGraphBuilder.authGraph() {
    navigation(
        route = AUTH_GRAPH,
        startDestination = AUTH_ROUTE,
    ) {
        composable(route = AUTH_ROUTE) {
            AuthMainRoute()
        }
    }
}
