package com.nilhcem.clickclick.service.helper

import android.content.Context
import com.nilhcem.clickclick.core.utils.AudioManagerUtils
import timber.log.Timber

class RouteAudioHelper(private val context: Context) {

    companion object {
        val DEVICE_NAME = "MiKeyRouteAudio"
    }

    fun enableAudioRouting() {
        Timber.d("MiKey is plugged in. Route audio to phone speaker")

        val typeWiredHeadset = 4
        val stateDisconnected = 0
        AudioManagerUtils.setWiredDeviceConnectionState(context, typeWiredHeadset, stateDisconnected, DEVICE_NAME)
    }
}
