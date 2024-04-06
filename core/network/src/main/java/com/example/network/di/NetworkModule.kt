package com.example.network.di

import com.example.network.api.MoneyTrackApi
import com.example.network.baseurl.BaseUrlProvider
import com.example.network.baseurl.EmulatorLocalHostBaseUrlProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    abstract fun bindsBaseUrlProvider(
        localBaseUrlProvider: EmulatorLocalHostBaseUrlProvider
    ): BaseUrlProvider


    companion object {
        @Singleton
        @Provides
        fun providesRetrofit(
            baseUrlProvider: BaseUrlProvider,
            client: OkHttpClient
        ): Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseUrlProvider.getBaseUrl())
                .client(client)
                .addConverterFactory(
                    Json.asConverterFactory("application/json".toMediaType())
                )
                .build()
        }

        @Provides
        @Singleton
        fun providesHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder().apply {
                addInterceptor(httpLoggingInterceptor)
            }.build()
        }

        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Singleton
        @Provides
        fun providesMoneyTrackApi(retrofit: Retrofit) = retrofit.create(MoneyTrackApi::class.java)
    }

}