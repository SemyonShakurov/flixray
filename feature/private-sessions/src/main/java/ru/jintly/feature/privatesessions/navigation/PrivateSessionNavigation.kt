package ru.jintly.feature.privatesessions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.jintly.feature.privatesessions.InputRoomNameRoute
import ru.jintly.feature.privatesessions.InvitePeopleRoute
import ru.jintly.feature.privatesessions.PrivateSessionsRoute

const val PRIVATE_SESSIONS_GRAPH = "private_sessions_graph"
const val PRIVATE_SESSIONS_ROUTE = "private_sessions_route"
const val INVITE_PEOPLE_ROUTE = "invite_people_route"
const val INPUT_ROOM_ROUTE = "input_room_route/{friend1}/{friend2}"

fun NavGraphBuilder.privateSessionsGraph(
    navController: NavController,
    onContinueClick: (String) -> Unit,
) {
    navigation(
        route = PRIVATE_SESSIONS_GRAPH,
        startDestination = PRIVATE_SESSIONS_ROUTE,
    ) {
        composable(
            route = PRIVATE_SESSIONS_ROUTE,
        ) {
            PrivateSessionsRoute(
                onMovieClick = {
                    navController.navigateToInvitePeople()
                },
            )
        }
        composable(route = INVITE_PEOPLE_ROUTE) {
            InvitePeopleRoute(onContinueClick = { friend1, friend2 ->
                navController.navigateToInputRoom(friend1, friend2)
            })
        }
        composable(
            route = INPUT_ROOM_ROUTE,
            arguments = listOf(
                navArgument("friend1") { type = NavType.StringType },
                navArgument("friend2") { type = NavType.StringType },
            ),
        ) {
            InputRoomNameRoute(onContinueClick)
        }
    }
}

private fun NavController.navigateToInvitePeople() {
    this.navigate(route = INVITE_PEOPLE_ROUTE)
}

private fun NavController.navigateToInputRoom(friend1: String, friend2: String) {
    this.navigate(route = "input_room_route/$friend1/$friend2")
}
