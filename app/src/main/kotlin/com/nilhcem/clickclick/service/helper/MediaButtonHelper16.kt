package com.nilhcem.clickclick.service.helper

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import com.nilhcem.clickclick.core.utils.AudioManagerUtils

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
class MediaButtonHelper16(context: Context) : MediaButtonHelper(context) {

    override fun registerMediaButtonEvents() {
        AudioManagerUtils.getAudioManager(context).registerMediaButtonEventReceiver(receiverComponent)
    }

    override fun unregisterMediaButtonEvents() {
        AudioManagerUtils.getAudioManager(context).unregisterMediaButtonEventReceiver(receiverComponent)
    }
}
