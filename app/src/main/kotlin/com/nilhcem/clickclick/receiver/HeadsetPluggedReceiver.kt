package com.nilhcem.clickclick.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.nilhcem.clickclick.model.app.SelectedDevice
import com.nilhcem.clickclick.repository.ConfigRepository
import com.nilhcem.clickclick.service.MiKeyService
import com.nilhcem.clickclick.ui.SelectDeviceActivity
import timber.log.Timber

class HeadsetPluggedReceiver : BroadcastReceiver() {

    companion object {
        private val CUSTOM_ACTION = "com.nilhcem.clickclick.action.ACTION_START"
        private val EXTRA_STATE = "state"
        private val EXTRA_MICROPHONE = "microphone"

        fun register(context: Context): HeadsetPluggedReceiver {
            val receiver = HeadsetPluggedReceiver()
            context.registerReceiver(receiver, IntentFilter(Intent.ACTION_HEADSET_PLUG))
            context.registerReceiver(receiver, IntentFilter(CUSTOM_ACTION))
            return receiver
        }

        fun unregister(context: Context, receiver: HeadsetPluggedReceiver) {
            context.unregisterReceiver(receiver)
        }

        fun notifyDevicePluggedIn(context: Context, device: SelectedDevice) {
            val intent = Intent(CUSTOM_ACTION)
            intent.putExtra(EXTRA_STATE, 1)
            intent.putExtra(EXTRA_MICROPHONE, if (device == SelectedDevice.MIKEY) 1 else 0)
            context.sendBroadcast(intent)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val config = ConfigRepository(context)
        val selectedDevice = config.getSelectedDevice()
        val state = intent.getIntExtra(EXTRA_STATE, 0)
        val hasMicrophone = intent.getIntExtra(EXTRA_MICROPHONE, 0) == 1

        if (state == 0) {
            if (selectedDevice != SelectedDevice.UNDEFINED) {
                onDeviceUnplugged(context, config, selectedDevice)
            }
        } else {
            if (state == 1 && hasMicrophone) {
                onMiKeyPluggedIn(context, config, selectedDevice)
            } else {
                onHeadsetPluggedIn(config, selectedDevice)
            }
        }
    }

    private fun onDeviceUnplugged(context: Context, config: ConfigRepository, selectedDevice: SelectedDevice) {
        when (selectedDevice) {
            SelectedDevice.PENDING -> {
                Timber.d("Dismiss device selection")
                SelectDeviceActivity.dismiss(context)
            }
            SelectedDevice.HEADSET -> {
                Timber.d("Headset unplugged")
            }
            SelectedDevice.MIKEY -> {
                Timber.d("MiKey unplugged")
                MiKeyService.start(context, false)
            }
        }

        config.setSelectedDevice(SelectedDevice.UNDEFINED)
    }

    private fun onHeadsetPluggedIn(config: ConfigRepository, selectedDevice: SelectedDevice) {
        Timber.d("Headset plugged in")

        if (selectedDevice != SelectedDevice.HEADSET) {
            config.setSelectedDevice(SelectedDevice.HEADSET)
        }
    }

    private fun onMiKeyPluggedIn(context: Context, config: ConfigRepository, selectedDevice: SelectedDevice) {
        if (selectedDevice == SelectedDevice.MIKEY) {
            Timber.d("MiKey plugged in")
            MiKeyService.start(context, true)
        } else {
            Timber.d("(MiKey || Headset) plugged in. Ask user to select the actual device")
            config.setSelectedDevice(SelectedDevice.PENDING)
            SelectDeviceActivity.show(context)
        }
    }
}
