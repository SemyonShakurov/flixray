package ru.jintly.publicsessions.data

import ru.jintly.core.network.api.NetworkApi
import javax.inject.Inject

class PublicSessionsRepository @Inject constructor(
    private val networkApi: NetworkApi,
) {

    suspend fun currentInfo(token: String) = networkApi.currentInfo(token).body()

    suspend fun availableRooms(token: String) = networkApi.availableRooms(token).body()
}
