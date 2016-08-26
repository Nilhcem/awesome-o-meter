package com.nilhcem.clickclick.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.nilhcem.clickclick.model.app.SelectedDevice
import com.nilhcem.clickclick.repository.ConfigRepository
import com.nilhcem.clickclick.service.MiKeyService
import com.nilhcem.clickclick.service.helper.RouteAudioHelper
import com.nilhcem.clickclick.ui.headset.SelectDeviceActivity
import timber.log.Timber

class HeadsetPluggedReceiver : BroadcastReceiver() {

    companion object {
        private val CUSTOM_ACTION = "com.nilhcem.clickclick.action.ACTION_START"
        private val EXTRA_STATE = "state"
        private val EXTRA_MICROPHONE = "microphone"

        // We must register this receiver programmatically
        // (due to HeadsetObserver sending FLAG_RECEIVER_REGISTERED_ONLY)
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
        val name = intent.getStringExtra("name") ?: intent.getStringExtra("portName")
        val state = intent.getIntExtra(EXTRA_STATE, 0)
        val hasMicrophone = intent.getIntExtra(EXTRA_MICROPHONE, 0) == 1

        if (name == RouteAudioHelper.DEVICE_NAME) {
            Timber.d("Received event from RouteAudioHelper")
            config.setSelectedDevice(SelectedDevice.UNDEFINED)

            // When the MiKeyService has been killed by the system, it is automatically restarted.
            // If this event is resent, we restart the media button receiver
            MiKeyService.enableMediaButtonReceiver(context)

            // Routing the audio means we pretend that the Mikey plugged in has been unplugged
            // This way, the OS will direct audio to the phone speakers instead of via the headset
            // However, the system will receive an event indicating that the headset was unplugged.
            // We ignore this event, as we want to intercept clicks from the MiKey
            return
        }

        if (state == 0) {
            if (selectedDevice != SelectedDevice.UNDEFINED) {
                onDeviceUnplugged(context, selectedDevice)
            }
        } else {
            if (state == 1 && hasMicrophone) {
                onMiKeyPluggedIn(context, selectedDevice, config)
            } else {
                onHeadsetPluggedIn(context, selectedDevice, config)
            }
        }
    }

    private fun onDeviceUnplugged(context: Context, selectedDevice: SelectedDevice) {
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
                MiKeyService.disableMediaButtonReceiver(context)
            }
        }
    }

    private fun onHeadsetPluggedIn(context: Context, selectedDevice: SelectedDevice, config: ConfigRepository) {
        Timber.d("Headset plugged in")

        if (selectedDevice != SelectedDevice.HEADSET) {
            config.setSelectedDevice(SelectedDevice.HEADSET)
        }
        MiKeyService.disableMediaButtonReceiver(context)
    }

    private fun onMiKeyPluggedIn(context: Context, selectedDevice: SelectedDevice, config: ConfigRepository) {
        if (selectedDevice == SelectedDevice.MIKEY) {
            Timber.d("MiKey plugged in")
            MiKeyService.enableAudioRouting(context)
        } else {
            Timber.d("(MiKey || Headset) plugged in. Ask user to select the actual device")
            config.setSelectedDevice(SelectedDevice.PENDING)
            SelectDeviceActivity.show(context)
        }
    }
}
