package ru.jintly.feature.privatesessions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.feature.privatesessions.InputRoomNameRoute
import ru.jintly.feature.privatesessions.InvitePeopleRoute
import ru.jintly.feature.privatesessions.PrivateSessionsRoute

const val PRIVATE_SESSIONS_GRAPH = "private_sessions_graph"
const val PRIVATE_SESSIONS_ROUTE = "private_sessions_route"
const val INVITE_PEOPLE_ROUTE = "invite_people_route"
const val INPUT_ROOM_ROUTE = "input_room_route"

fun NavGraphBuilder.privateSessionsGraph(
    navController: NavController,
    onContinueClick: () -> Unit,
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
            InvitePeopleRoute(onContinueClick = { navController.navigateToInputRoom() })
        }
        composable(route = INPUT_ROOM_ROUTE) {
            InputRoomNameRoute(onContinueClick)
        }
    }
}

private fun NavController.navigateToInvitePeople() {
    this.navigate(route = INVITE_PEOPLE_ROUTE)
}

private fun NavController.navigateToInputRoom() {
    this.navigate(route = INPUT_ROOM_ROUTE)
}
