package ru.jintly.player.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.jintly.player.presentation.PlayerRoute

private const val PLAYER_ROUTE = "player_route/{is_private}/{is_admin}"

fun NavController.navigateToPlayer(isPrivate: Boolean, isAdmin: Boolean) {
    this.navigate("player_route/$isPrivate/$isAdmin")
}

fun NavGraphBuilder.playerScreen() {
    composable(
        route = PLAYER_ROUTE,
        arguments = listOf(
            navArgument("is_private") { type = NavType.BoolType },
            navArgument("is_admin") { type = NavType.BoolType },
        ),
    ) {
        PlayerRoute()
    }
}
