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
private const val AUTH_INPUT_CODE_ROUTE = "auth_input_code_route/{code}/{email}"
private const val AUTH_CREATE_PASSWORD_ROUTE = "auth_create_password_route/{email}"
private const val AUTH_ACCOUNT_INFO_ROUTE = "auth_account_info_route/{password}/{email}"
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
                onSendCodeSuccess = { code, email ->
                    navController.navigateToInputCode(code, email)
                },
            )
        }
        composable(route = AUTH_PASSWORD_RECOVERY_ROUTE) {
            AuthPasswordRecoveryRoute()
        }
        composable(
            route = AUTH_INPUT_CODE_ROUTE,
            arguments = listOf(
                navArgument("code") { type = NavType.IntType },
                navArgument("email") { type = NavType.StringType },
            ),
        ) {
            AuthInputCodeRoute(
                onConfirmCodeSuccess = { email ->
                    navController.navigateToCreatePassword(email)
                },
            )
        }
        composable(
            route = AUTH_CREATE_PASSWORD_ROUTE,
            arguments = listOf(navArgument("email") { type = NavType.StringType }),
        ) {
            AuthCreatePasswordRoute(onAuthSuccess = { password, email ->
                navController.navigateToAccountInfo(password, email)
            })
        }
        composable(
            route = AUTH_ACCOUNT_INFO_ROUTE,
            arguments = listOf(
                navArgument("password") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
            ),
        ) {
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

private fun NavController.navigateToInputCode(code: Int, email: String) {
    this.navigate(route = "auth_input_code_route/$code/$email")
}

private fun NavController.navigateToCreatePassword(email: String) {
    this.navigate(route = "auth_create_password_route/$email")
}

private fun NavController.navigateToAccountInfo(password: String, email: String) {
    this.navigate(route = "auth_account_info_route/$password/$email")
}

private fun NavController.navigateToAvatar() {
    this.navigate(route = AUTH_AVATAR_ROUTE)
}
