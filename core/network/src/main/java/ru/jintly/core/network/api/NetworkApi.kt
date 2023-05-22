package ru.jintly.core.network.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkApi {

    @POST(value = "srv/auth/api/confirm")
    suspend fun confirmCode(
        @Body data: ConfirmRequest,
    ): Response<Unit>

    suspend fun register()

    companion object {
        internal const val BASE_URL = "http://app.flixray.ru"
    }
}

@Serializable
data class ConfirmRequest(
    val email: String,
    val code: Int,
)
