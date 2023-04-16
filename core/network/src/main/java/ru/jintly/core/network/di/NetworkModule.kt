package ru.jintly.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.jintly.core.network.api.NetworkApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesOkHttpCallFactory(): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    },
            )
            .build()

    @Provides
    @Singleton
    fun providesNetworkApi(
        okHttpCallFactory: Call.Factory,
        networkJson: Json,
    ): NetworkApi = Retrofit.Builder()
        .baseUrl(NetworkApi.BASE_URL)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(NetworkApi::class.java)
}
