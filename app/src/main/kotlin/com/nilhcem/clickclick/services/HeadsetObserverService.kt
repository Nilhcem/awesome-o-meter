package com.nilhcem.clickclick.services

import android.app.Service
import android.content.Context
import android.content.Intent
import com.nilhcem.clickclick.services.receivers.HeadsetPluggedReceiver
import timber.log.Timber

class HeadsetObserverService : Service() {

    companion object {
        fun start(context: Context) {
            context.startService(Intent(context, HeadsetObserverService::class.java))
        }
    }

    private lateinit var headsetPluggedReceiver: HeadsetPluggedReceiver

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        if (intent == null) {
            Timber.d("intent is null, service has been restarted")
        }

        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")

        // We must register this receiver programmatically (FLAG_RECEIVER_REGISTERED_ONLY)
        headsetPluggedReceiver = HeadsetPluggedReceiver.register(this)
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        HeadsetPluggedReceiver.unregister(this, headsetPluggedReceiver)
        super.onDestroy()
    }
}
