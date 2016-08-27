package com.nilhcem.clickclick.service.helper

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class MediaButtonHelper21(context: Context) : MediaButtonHelper(context) {

    private var mediaSession: MediaSession? = null

    override fun registerMediaButtonEvents() {
        startMediaSession()
    }

    override fun unregisterMediaButtonEvents() {
        stopMediaSession()
    }

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
