package ru.jintly.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.jintly.core.data.utils.NetworkMonitor
import ru.jintly.core.data.utils.NetworkMonitorImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindNetworkMonitor(
        impl: NetworkMonitorImpl,
    ): NetworkMonitor
}
