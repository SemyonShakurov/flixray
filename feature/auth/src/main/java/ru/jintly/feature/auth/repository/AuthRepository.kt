package ru.jintly.feature.auth.repository

import ru.jintly.core.network.api.ConfirmRequest
import ru.jintly.core.network.api.EmailRegRequest
import ru.jintly.core.network.api.NetworkApi
import ru.jintly.core.network.api.RegisterRequest
import ru.jintly.core.network.api.TokenRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val networkApi: NetworkApi,
) {

    suspend fun confirmCode(email: String, code: Int) =
        networkApi.confirmCode(ConfirmRequest(email, code))

    suspend fun regEmail(email: String) = networkApi.regEmail(EmailRegRequest(email)).body()

    suspend fun register(email: String, password: String, name: String) = networkApi.register(
        RegisterRequest(name, email, password),
    ).body()

    suspend fun token(email: String, password: String) = networkApi.token(
        TokenRequest(email, password),
    ).body()
}
