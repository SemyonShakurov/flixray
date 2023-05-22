package ru.jintly.core.network.api

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val data: T,
)
