package com.example.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.datastore.model.UserDataProto
import com.example.datastore.serializer.userDataDataStore
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
    fun provides(@ApplicationContext appContext: Context): DataStore<UserDataProto?> {
        return appContext.userDataDataStore
    }
}