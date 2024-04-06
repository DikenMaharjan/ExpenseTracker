package com.example.moneytrack.navigation

import android.net.Uri
import java.net.URLDecoder

fun buildNavigationRoute(
    root: String,
    keys: List<String> = emptyList(),
    optionalKeys: List<String> = emptyList()
): String {
    return Uri.Builder()
        .path(root)
        .apply {
            keys.forEach {
                appendPath("{$it}")
            }

            optionalKeys.forEach {
                appendQueryParameter(it, "{$it}")
            }
        }
        .build()
        .toString().run {
            URLDecoder.decode(this, "UTF-8")
        }
}

fun buildNavigationPath(
    root: String,
    values: List<String> = emptyList(),
    optionalValues: Map<String, String> = emptyMap()
): String {
    return Uri.Builder()
        .path(root)
        .apply {
            values.forEach {
                appendPath(it)
            }

            optionalValues.forEach { (key, value) ->
                appendQueryParameter(key, value)
            }
        }
        .build()
        .toString()
}
