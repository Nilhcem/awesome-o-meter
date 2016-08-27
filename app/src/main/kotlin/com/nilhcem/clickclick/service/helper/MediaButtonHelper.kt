package com.nilhcem.clickclick.service.helper

import android.content.Context
import android.os.Build
import com.nilhcem.clickclick.receiver.MediaButtonReceiver
import timber.log.Timber

abstract class MediaButtonHelper(protected val context: Context) {

    companion object {
        fun create(context: Context): MediaButtonHelper {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return MediaButtonHelper21(context)
            } else {
                return MediaButtonHelper16(context)
            }
        }
    }

    protected val receiverComponent = MediaButtonReceiver.getComponentName(context)

    fun enableReceiver() {
        Timber.d("Register MediaButtonReceiver")
        MediaButtonReceiver.enable(context)
        registerMediaButtonEvents()
    }

    fun disableReceiver() {
        Timber.d("Unregister MediaButtonReceiver")
        MediaButtonReceiver.disable(context)
        unregisterMediaButtonEvents()
    }

    protected abstract fun registerMediaButtonEvents()
    protected abstract fun unregisterMediaButtonEvents()
}
