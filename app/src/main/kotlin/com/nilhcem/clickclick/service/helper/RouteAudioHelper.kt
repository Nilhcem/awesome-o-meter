package com.nilhcem.clickclick.service.helper

import android.content.Context
import com.nilhcem.clickclick.core.utils.AudioManagerUtils
import timber.log.Timber

class RouteAudioHelper(private val context: Context) {

    companion object {
        val DEVICE_NAME = "MiKeyRouteAudio"
    }

    fun enable() {
        Timber.d("MiKey is plugged in. Route audio to phone speaker")

        val wiredHeadsetType = 4
        val disconnectedState = 0
        AudioManagerUtils.setWiredDeviceConnectionState(context, wiredHeadsetType, disconnectedState, DEVICE_NAME)
    }
}
