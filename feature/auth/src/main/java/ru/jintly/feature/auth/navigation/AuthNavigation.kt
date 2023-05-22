package ru.jintly.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.jintly.feature.auth.AuthMainRoute
import ru.jintly.feature.auth.accountinfo.AccountInfoRoute
import ru.jintly.feature.auth.accountinfo.AvatarScreenRoute
import ru.jintly.feature.auth.login.AuthLoginRoute
import ru.jintly.feature.auth.passwordrecovery.AuthPasswordRecoveryRoute
import ru.jintly.feature.auth.register.AuthCreatePasswordRoute
import ru.jintly.feature.auth.register.AuthInputCodeRoute
import ru.jintly.feature.auth.register.AuthRegisterRoute

const val AUTH_GRAPH = "auth_graph"

private const val AUTH_MAIN_ROUTE = "auth_route"
private const val AUTH_LOGIN_ROUTE = "auth_login_route"
private const val AUTH_REGISTER_ROUTE = "auth_register_route"
private const val AUTH_PASSWORD_RECOVERY_ROUTE = "auth_password_recovery_route"
private const val AUTH_INPUT_CODE_ROUTE = "auth_input_code_route/{email}"
private const val AUTH_CREATE_PASSWORD_ROUTE = "auth_create_password_route"
private const val AUTH_ACCOUNT_INFO_ROUTE = "auth_account_info_route"
private const val AUTH_AVATAR_ROUTE = "auth_avatar_route"

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onAuthSuccess: () -> Unit,
) {
    navigation(
        route = AUTH_GRAPH,
        startDestination = AUTH_LOGIN_ROUTE,
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
                onAuthSuccess = onAuthSuccess,
            )
        }
        composable(route = AUTH_REGISTER_ROUTE) {
            AuthRegisterRoute(
                onSendCodeSuccess = { email ->
                    navController.navigateToInputCode(email)
                },
            )
        }
        composable(route = AUTH_PASSWORD_RECOVERY_ROUTE) {
            AuthPasswordRecoveryRoute()
        }
        composable(
            route = AUTH_INPUT_CODE_ROUTE,
            arguments = listOf(navArgument("email") { type = NavType.StringType }),
        ) {
            AuthInputCodeRoute(
                onConfirmCodeSuccess = {
                    navController.navigateToCreatePassword()
                },
            )
        }
        composable(route = AUTH_CREATE_PASSWORD_ROUTE) {
            AuthCreatePasswordRoute(onAuthSuccess = {
                navController.navigateToAccountInfo()
            })
        }
        composable(route = AUTH_ACCOUNT_INFO_ROUTE) {
            AccountInfoRoute(
                onProfileInfoInputComplete = { navController.navigateToAvatar() },
            )
        }
        composable(route = AUTH_AVATAR_ROUTE) {
            AvatarScreenRoute(onAuthSuccess)
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

private fun NavController.navigateToInputCode(email: String) {
    this.navigate(route = "auth_input_code_route/$email")
}

private fun NavController.navigateToCreatePassword() {
    this.navigate(route = AUTH_CREATE_PASSWORD_ROUTE)
}

private fun NavController.navigateToAccountInfo() {
    this.navigate(route = AUTH_ACCOUNT_INFO_ROUTE)
}

private fun NavController.navigateToAvatar() {
    this.navigate(route = AUTH_AVATAR_ROUTE)
}
