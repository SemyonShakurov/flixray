package ru.jintly.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.feature.profile.ProfileRoute

const val PROFILE_GRAPH = "profile_graph"
const val PROFILE_ROUTE = "profile_route"

fun NavGraphBuilder.profileGraph() {
    navigation(
        route = PROFILE_GRAPH,
        startDestination = PROFILE_ROUTE,
    ) {
        composable(route = PROFILE_ROUTE) {
            ProfileRoute()
        }
    }
}
