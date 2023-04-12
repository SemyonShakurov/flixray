package ru.jintly.player.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.jintly.player.presentation.PlayerRoute

private const val PLAYER_ROUTE = "player_route"

fun NavController.navigateToPlayer() {
    this.navigate(PLAYER_ROUTE)
}

fun NavGraphBuilder.playerScreen() {
    composable(route = PLAYER_ROUTE) {
        PlayerRoute()
    }
}
