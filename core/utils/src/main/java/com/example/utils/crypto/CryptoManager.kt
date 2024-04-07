package com.example.utils.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.nio.ByteBuffer
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


class CryptoManager @AssistedInject constructor(
    @Assisted private val keyAlias: String,
) {

    private val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
        load(null)
    }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun getEncryptCipher() = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey())
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM, ANDROID_KEY_STORE).apply {
            init(
                KeyGenParameterSpec.Builder(
                    keyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(BLOCK_MODE).setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false).setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun encrypt(input: ByteArray): ByteArray {
        val encryptCipher = getEncryptCipher()
        val encryptedBytes = encryptCipher.doFinal(input)

        val ivSizeBytes = encryptCipher.iv.size.getBytes()

        return ivSizeBytes + encryptCipher.iv + encryptedBytes
    }

    private fun Int.getBytes(): ByteArray =
        ByteBuffer.allocate(Int.SIZE_BYTES).putInt(this).array()

    private fun ByteArray.bytesToInt(): Int = ByteBuffer.wrap(this).int

    fun decrypt(byteArray: ByteArray): ByteArray? {
        return try {
            val ivSize = byteArray.copyOfRange(0, Int.SIZE_BYTES).bytesToInt()
            val iv = byteArray.copyOfRange(Int.SIZE_BYTES, ivSize + Int.SIZE_BYTES)
            val decryptCipher = getDecryptCipherForIv(iv)
            val encryptedBytes = byteArray.copyOfRange(ivSize + Int.SIZE_BYTES, byteArray.size)
            decryptCipher.doFinal(encryptedBytes)
        } catch (e: Throwable) {
            null
        }
    }


    companion object {
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

}


@AssistedFactory
interface CryptoManagerFactory {
    fun create(alias: String): CryptoManager
}