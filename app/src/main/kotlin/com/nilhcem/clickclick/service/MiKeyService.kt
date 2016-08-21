package com.nilhcem.clickclick.service

import android.app.Service
import android.content.Context
import android.content.Intent
import com.nilhcem.clickclick.receiver.HeadsetPluggedReceiver
import com.nilhcem.clickclick.service.helper.MediaButtonHelper
import timber.log.Timber

class MiKeyService : Service() {

    companion object {
        private val EXTRA_MIKEY_ENABLED = "miKeyEnabled"

        fun start(context: Context, miKeyEnabled: Boolean? = null) {
            val intent = Intent(context, MiKeyService::class.java)
            if (miKeyEnabled != null) {
                intent.putExtra(EXTRA_MIKEY_ENABLED, miKeyEnabled)
            }
            context.startService(intent)
        }
    }

    private lateinit var headsetPluggedReceiver: HeadsetPluggedReceiver
    private lateinit var mediaButtonHelper: MediaButtonHelper

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        if (intent == null) {
            Timber.d("intent is null, service has been restarted")
        } else {
            if (intent.hasExtra(EXTRA_MIKEY_ENABLED)) {
                if (intent.getBooleanExtra(EXTRA_MIKEY_ENABLED, false)) {
                    Timber.d("Enable MiKey service features")
                    mediaButtonHelper.enableReceiver()
                    // TODO: enableAudioRouting()
                } else {
                    Timber.d("Disable MiKey service features")
                    mediaButtonHelper.disableReceiver()
                    // TODO: disableAudioRouting()
                }
            }
        }
        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        headsetPluggedReceiver = HeadsetPluggedReceiver.register(this)
        mediaButtonHelper = MediaButtonHelper(this)
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        HeadsetPluggedReceiver.unregister(this, headsetPluggedReceiver)
        mediaButtonHelper.disableReceiver()
        super.onDestroy()
    }
}
