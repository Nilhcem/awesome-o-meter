package com.nilhcem.clickclick.service.helper

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.session.MediaSession
import android.os.Build
import com.nilhcem.clickclick.receiver.MediaButtonReceiver
import timber.log.Timber

class MediaButtonHelper(private val context: Context) {

    private val receiverComponent = MediaButtonReceiver.getComponentName(context)
    private var mediaSession: MediaSession? = null

    fun enableReceiver() {
        Timber.d("Register MediaButtonReceiver")
        MediaButtonReceiver.enable(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startMediaSession()
        } else {
            getAudioManager().registerMediaButtonEventReceiver(receiverComponent)
        }
    }

    fun disableReceiver() {
        Timber.d("Unregister MediaButtonReceiver")
        MediaButtonReceiver.disable(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stopMediaSession()
        } else {
            getAudioManager().unregisterMediaButtonEventReceiver(receiverComponent)
        }
    }

    private fun getAudioManager() = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startMediaSession() {
        val session = MediaSession(context, javaClass.simpleName)

        session.setCallback(object : MediaSession.Callback() {
            override fun onMediaButtonEvent(mediaButtonIntent: Intent): Boolean {
                // Make app the sole receiver of ACTION_MEDIA_BUTTON
                mediaButtonIntent.component = receiverComponent
                context.sendBroadcast(mediaButtonIntent)
                return true
            }
        })
        session.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS)
        session.isActive = true

        mediaSession = session
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun stopMediaSession() {
        val session = mediaSession

        if (session != null) {
            session.isActive = false
            session.setCallback(null)
            session.release()
            mediaSession = null
        }
    }
}
