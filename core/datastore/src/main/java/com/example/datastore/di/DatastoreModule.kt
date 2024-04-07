package com.example.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.model.UserDataProto
import com.example.datastore.serializer.EncryptedSerializerFactory
import com.example.datastore.serializer.UserDataSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provides(
        @ApplicationContext appContext: Context,
        encryptedSerializerFactory: EncryptedSerializerFactory<UserDataProto?>
    ): DataStore<UserDataProto?> {
        val userEncryptedSerializer = encryptedSerializerFactory.create(
            UserDataSerializer
        )
        return DataStoreFactory.create(
            serializer = userEncryptedSerializer,
            produceFile = { appContext.dataStoreFile("userData.pb") },
            corruptionHandler = ReplaceFileCorruptionHandler { null }
        )
    }

    @Singleton
    @Provides
    fun providesUserPreferencesDataStore(
        @ApplicationContext appContext: Context
    ) = appContext.userPreferencesDataStore

}

val Context.userPreferencesDataStore by preferencesDataStore("userpreferences.")