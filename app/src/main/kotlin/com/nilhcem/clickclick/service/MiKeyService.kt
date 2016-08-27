package com.nilhcem.clickclick.service

import android.app.Service
import android.content.Context
import android.content.Intent
import com.nilhcem.clickclick.receiver.HeadsetPluggedReceiver
import com.nilhcem.clickclick.service.helper.MediaButtonHelper
import com.nilhcem.clickclick.service.helper.RouteAudioHelper
import timber.log.Timber

class MiKeyService : Service() {

    companion object {
        private val EXTRA_ROUTE_AUDIO = "routeAudio"
        private val EXTRA_ENABLE_MEDIA_BUTTON = "mediaButtonEnabled"

        fun start(context: Context) {
            context.startService(createIntent(context))
        }

        fun enableAudioRouting(context: Context) {
            val intent = createIntent(context)
            intent.putExtra(EXTRA_ROUTE_AUDIO, true)
            context.startService(intent)
        }

        fun enableMediaButtonReceiver(context: Context) =
                setMediaButtonReceiverState(context, true)

        fun disableMediaButtonReceiver(context: Context) =
                setMediaButtonReceiverState(context, false)

        private fun createIntent(context: Context) = Intent(context, MiKeyService::class.java)

        private fun setMediaButtonReceiverState(context: Context, enabled: Boolean) {
            val intent = createIntent(context)
            intent.putExtra(EXTRA_ENABLE_MEDIA_BUTTON, enabled)
            context.startService(intent)
        }
    }

    private lateinit var headsetPluggedReceiver: HeadsetPluggedReceiver
    private lateinit var routeAudioHelper: RouteAudioHelper
    private lateinit var mediaButtonHelper: MediaButtonHelper

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        if (intent == null) {
            Timber.d("intent is null, service has been restarted")
        } else {
            if (intent.hasExtra(EXTRA_ROUTE_AUDIO)) {
                routeAudioHelper.enable()
            } else if (intent.hasExtra(EXTRA_ENABLE_MEDIA_BUTTON)) {
                if (intent.getBooleanExtra(EXTRA_ENABLE_MEDIA_BUTTON, false)) {
                    mediaButtonHelper.enable()
                } else {
                    mediaButtonHelper.disable()
                }
            }
        }
        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        headsetPluggedReceiver = HeadsetPluggedReceiver.register(this)
        routeAudioHelper = RouteAudioHelper(this)
        mediaButtonHelper = MediaButtonHelper.create(this)
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        mediaButtonHelper.disable()
        HeadsetPluggedReceiver.unregister(this, headsetPluggedReceiver)
        super.onDestroy()
    }
}
