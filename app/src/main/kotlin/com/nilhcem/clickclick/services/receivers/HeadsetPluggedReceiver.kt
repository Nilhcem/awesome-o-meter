package com.nilhcem.clickclick.services.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import timber.log.Timber

class HeadsetPluggedReceiver : BroadcastReceiver() {

    companion object {
        fun register(context: Context): HeadsetPluggedReceiver {
            val receiver = HeadsetPluggedReceiver()
            context.registerReceiver(receiver, IntentFilter(Intent.ACTION_HEADSET_PLUG))
            return receiver
        }

        fun unregister(context: Context, receiver: HeadsetPluggedReceiver) {
            context.unregisterReceiver(receiver)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val isPlugged = intent.getIntExtra("state", 0) != 0
        val hasMicrophone = intent.getIntExtra("microphone", 0) == 1

        if (isPlugged) {
            // Mi Key is considered as having a microphone
            Timber.i("Headset plugged ${intent.getIntExtra("state", 0)} - hasMic: $hasMicrophone")
        } else {
            Timber.i("Headset unplugged")
        }
    }
}
