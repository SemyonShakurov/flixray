package ru.jintly.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.jintly.feature.auth.AuthMainRoute
import ru.jintly.feature.auth.login.AuthLoginRoute
import ru.jintly.feature.auth.passwordrecovery.AuthPasswordRecoveryRoute
import ru.jintly.feature.auth.register.AuthInputCodeRoute
import ru.jintly.feature.auth.register.AuthRegisterRoute

const val AUTH_GRAPH = "auth_graph"

private const val AUTH_MAIN_ROUTE = "auth_route"
private const val AUTH_LOGIN_ROUTE = "auth_login_route"
private const val AUTH_REGISTER_ROUTE = "auth_register_route"
private const val AUTH_PASSWORD_RECOVERY_ROUTE = "auth_password_recovery_route"
private const val AUTH_INPUT_CODE_ROUTE = "auth_input_code_route"

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    navigation(
        route = AUTH_GRAPH,
        startDestination = AUTH_MAIN_ROUTE,
    ) {
        composable(route = AUTH_MAIN_ROUTE) {
            AuthMainRoute(
                onEnterClick = {
                    navController.navigateToLogin()
                },
                onRegisterClick = {
                    navController.navigateToRegister()
                },
            )
        }
        composable(route = AUTH_LOGIN_ROUTE) {
            AuthLoginRoute(
                onForgotPasswordClick = {
                    navController.navigateToPasswordRecovery()
                },
                onRegisterClick = {
                    navController.navigateToRegister()
                },
            )
        }
        composable(route = AUTH_REGISTER_ROUTE) {
            AuthRegisterRoute(
                onSendCodeSuccess = {
                    navController.navigateToInputCode()
                },
            )
        }
        composable(route = AUTH_PASSWORD_RECOVERY_ROUTE) {
            AuthPasswordRecoveryRoute()
        }
        composable(route = AUTH_INPUT_CODE_ROUTE) {
            AuthInputCodeRoute()
        }
    }
}

private fun NavController.navigateToLogin() {
    this.navigate(route = AUTH_LOGIN_ROUTE)
}

private fun NavController.navigateToRegister() {
    this.navigate(route = AUTH_REGISTER_ROUTE)
}

private fun NavController.navigateToPasswordRecovery() {
    this.navigate(route = AUTH_PASSWORD_RECOVERY_ROUTE)
}

private fun NavController.navigateToInputCode() {
    this.navigate(route = AUTH_INPUT_CODE_ROUTE)
}
