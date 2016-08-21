package com.nilhcem.clickclick.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nilhcem.clickclick.service.MiKeyService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        MiKeyService.start(context)
    }
}
