package com.nilhcem.clickclick.services.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nilhcem.clickclick.services.HeadsetObserverService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        HeadsetObserverService.start(context)
    }
}
