package com.example.datastore.serializer

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.datastore.model.UserDataProto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserDataSerializer : Serializer<UserDataProto?> {
    override val defaultValue: UserDataProto? = null

    override suspend fun readFrom(input: InputStream): UserDataProto? {
        val json = input.readBytes().decodeToString()
        return try {
            Json.decodeFromString(UserDataProto.serializer(), json)
        } catch (e: SerializationException) {
            null
        }
    }

    override suspend fun writeTo(t: UserDataProto?, output: OutputStream) {
        output.use {
            it.write(Json.encodeToString(t).toByteArray())
        }
    }
}

val Context.userDataDataStore: DataStore<UserDataProto?> by dataStore(
    fileName = "userData.pb",
    serializer = UserDataSerializer
)