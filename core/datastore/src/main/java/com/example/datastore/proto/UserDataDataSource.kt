package com.example.datastore.proto

import androidx.datastore.core.DataStore
import com.example.datastore.model.UserDataProto
import com.example.utils.di.ApplicationScope
import com.example.utils.dispatcher.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@Singleton
class UserDataDataSource @Inject constructor(
    private val userDataDataStore: DataStore<UserDataProto?>,
    private val appDispatchers: AppDispatchers,
    @ApplicationScope private val scope: CoroutineScope
) {

    val userData = userDataDataStore.data

    val loggedInUserID = userData
        .map { it?.id }
        .distinctUntilChanged()
        .stateIn(scope, SharingStarted.Lazily, null)

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

    @OptIn(ExperimentalContracts::class)
    inline fun withUserID(invoke: (String) -> Unit) {
        contract {
            callsInPlace(invoke, InvocationKind.AT_MOST_ONCE)
        }
        val userID = loggedInUserID.value
        if (userID != null) {
            invoke(userID)
        }
    }
}