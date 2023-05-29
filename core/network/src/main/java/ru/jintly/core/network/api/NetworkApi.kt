package ru.jintly.core.network.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkApi {

    @POST(value = "srv/auth/api/confirm")
    suspend fun confirmCode(
        @Body data: ConfirmRequest,
    ): Response<Unit>

    @POST(value = "https://app.flixray.ru/srv/auth/api/reg_mail")
    suspend fun regEmail(
        @Body data: EmailRegRequest,
    ): Response<EmailRegResponse>

    @POST(value = "srv/auth/api/reg_user")
    suspend fun register(
        @Body data: RegisterRequest,
    ): Response<RegisterResponse>

    @POST(value = "srv/auth/api/token")
    suspend fun token(
        @Body data: TokenRequest,
    ): Response<TokenResponse>

    @GET(value = "srv/user_api/api/find_by_name")
    suspend fun findByName(
        @Query("token") token: String,
        @Query("name") name: String,
        @Header("User-Agent") a: String = "PostmanRuntime/7.32.2",
        @Header("Accept") b: String = "*/*",
        @Header("Accept-Encoding") c: String = "gzip, deflate, br",
        @Header("Connection") d: String = "keep-alive",
    ): Response<FindByNameResponse?>

    @POST(value = "srv/room_api/api/create")
    suspend fun createRoom(
        @Query("token") token: String,
        @Body data: CreateRoomRequest,
        @Header("Content-Type") contentType: String = "application/json",
    ): Response<Unit>

    companion object {
        internal const val BASE_URL = "http://app.flixray.ru"
    }
}

@Serializable
data class ConfirmRequest(
    val email: String,
    val code: Int,
)

@Serializable
data class EmailRegRequest(
    val email: String,
)

@Serializable
data class EmailRegResponse(
    val code: Int,
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)

@Serializable
data class RegisterResponse(
    val name: String,
    val email: String,
)

@Serializable
data class TokenRequest(
    val email: String,
    val password: String,
)

@Serializable
data class TokenResponse(
    val token: String,
    val name: String,
)

@Serializable
data class FindByNameResponse(
    val email: String,
    val name: String,
)

@Serializable
data class CreateRoomRequest(
    val name: String,
    val is_private: Boolean,
    val email_list: List<String>,
)
