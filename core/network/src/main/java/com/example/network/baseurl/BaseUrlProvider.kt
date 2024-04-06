package com.example.network.baseurl

import javax.inject.Inject

interface BaseUrlProvider {
    fun getBaseUrl(): String
}

class EmulatorLocalHostBaseUrlProvider @Inject constructor() : BaseUrlProvider {
    override fun getBaseUrl(): String {
        return "http://10.0.2.2:4000"
    }
}

class LocalHostBaseUrlProvider @Inject constructor() : BaseUrlProvider {
    override fun getBaseUrl(): String {
        return "http://localhost:4000"
    }
}

class RemoteLocalHostDeviceBaseUrlProvider @Inject constructor() : BaseUrlProvider {
    override fun getBaseUrl(): String {
        //todo replace with the local ip of the server.
        return ""
    }

}