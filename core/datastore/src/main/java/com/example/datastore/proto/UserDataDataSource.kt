package com.example.datastore.proto

import androidx.datastore.core.DataStore
import com.example.datastore.model.UserDataProto
import com.example.utils.dispatcher.AppDispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataDataSource @Inject constructor(
    private val userDataDataStore: DataStore<UserDataProto?>,
    private val appDispatchers: AppDispatchers
) {

    val userData = userDataDataStore.data
    fun getUserDataBlocking() = runBlocking { userData.first() }

    suspend fun storeUserData(
        name: String,
        id: String
    ) {
        return withContext(appDispatchers.background + NonCancellable) {
            userDataDataStore.updateData {
                UserDataProto(
                    name = name,
                    id = id
                )
            }
        }
    }
}