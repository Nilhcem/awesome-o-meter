package com.nilhcem.clickclick.receiver

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.KeyEvent
import com.nilhcem.clickclick.core.utils.Preconditions
import com.nilhcem.clickclick.core.utils.Preconditions.checkNotNull
import com.nilhcem.clickclick.repository.ClickRepository
import timber.log.Timber

class MediaButtonReceiver : BroadcastReceiver() {

    companion object {
        private val ACTION_LONG_CLICK = "com.nilhcem.clickclick.action.LONG_CLICK"

        fun enable(context: Context) = setComponentEnable(context, true)
        fun disable(context: Context) = setComponentEnable(context, false)
        fun getComponentName(context: Context) = ComponentName(context, MediaButtonReceiver::class.java)
        fun receiveLongClick(context: Context) = context.sendBroadcast(Intent(ACTION_LONG_CLICK))

        private fun setComponentEnable(context: Context, enable: Boolean) {
            val newState = if (enable) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            context.packageManager.setComponentEnabledSetting(getComponentName(context), newState, PackageManager.DONT_KILL_APP)
        }
    }

    private val clickRepo = ClickRepository()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_LONG_CLICK) {
            Timber.i("Long click received")
            clickRepo.insert()
        } else {
            Preconditions.checkArgument(intent.action == Intent.ACTION_MEDIA_BUTTON)
            val keyEvent = checkNotNull(intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT))
            if (keyEvent.action == KeyEvent.ACTION_UP) {
                Timber.i("Click received")
                clickRepo.insert()
            }
        }
    }
}
