package com.example.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.utils.crypto.CryptoManagerFactory
import com.example.utils.dispatcher.AppDispatchers
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream


class EncryptedSerializer<T> @AssistedInject constructor(
    @Assisted val delegate: Serializer<T>,
    cryptoManagerFactory: CryptoManagerFactory,
    private val appDispatchers: AppDispatchers
) : Serializer<T> {

    private val cryptoManager = cryptoManagerFactory.create("EncryptedKey")
    override val defaultValue: T = delegate.defaultValue

    override suspend fun readFrom(input: InputStream): T {
        try {
            val encrypted = input.readBytes()
            val bytes = cryptoManager.decrypt(encrypted)
            return delegate.readFrom(bytes!!.inputStream())
        } catch (e: Exception) {
            throw CorruptionException("Cannot read the stream.")
        }
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        withContext(appDispatchers.background) {
            val outputStream = ByteArrayOutputStream()
            delegate.writeTo(t, outputStream)
            val encrypted = cryptoManager.encrypt(outputStream.toByteArray())
            output.write(encrypted)
        }
    }

}

@AssistedFactory
interface EncryptedSerializerFactory<T> {
    fun create(delegate: Serializer<T>): EncryptedSerializer<T>
}