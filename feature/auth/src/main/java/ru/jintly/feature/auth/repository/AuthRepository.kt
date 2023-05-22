package ru.jintly.feature.auth.repository

import ru.jintly.core.network.api.ConfirmRequest
import ru.jintly.core.network.api.NetworkApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val networkApi: NetworkApi,
) {

    suspend fun confirmCode(email: String, code: Int) = networkApi.confirmCode(ConfirmRequest(email, code))
}
