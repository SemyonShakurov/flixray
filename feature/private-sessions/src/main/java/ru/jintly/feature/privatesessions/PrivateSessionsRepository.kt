package ru.jintly.feature.privatesessions

import ru.jintly.core.network.api.CreateRoomRequest
import ru.jintly.core.network.api.NetworkApi
import javax.inject.Inject

class PrivateSessionsRepository @Inject constructor(
    private val networkApi: NetworkApi,
) {

    suspend fun findByName(token: String, name: String) =
        networkApi.findByName(token, name = name).body()

    suspend fun createRoom(token: String, name: String, emailList: List<String>) =
        networkApi.createRoom(
            token,
            CreateRoomRequest(
                name,
                true,
                emailList,
            ),
        ).body()

    suspend fun start(token: String, roomName: String) = networkApi.start(
        token,
        roomName,
    ).body()
}
