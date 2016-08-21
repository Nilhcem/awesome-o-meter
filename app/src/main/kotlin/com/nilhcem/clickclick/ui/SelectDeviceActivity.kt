package com.nilhcem.clickclick.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.model.app.SelectedDevice
import com.nilhcem.clickclick.repository.ConfigRepository
import com.nilhcem.clickclick.receiver.HeadsetPluggedReceiver

class SelectDeviceActivity : AppCompatActivity() {

    companion object {
        val EXTRA_DISMISS = "dismiss"

        fun show(context: Context) {
            context.startActivity(createStartIntent(context))
        }

        fun dismiss(context: Context) {
            val intent = createStartIntent(context)
            intent.putExtra(EXTRA_DISMISS, true)
            context.startActivity(intent)
        }

        private fun createStartIntent(context: Context): Intent {
            val intent = Intent(context, SelectDeviceActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

    private lateinit var dialog: AlertDialog
    private lateinit var config: ConfigRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = AlertDialog.Builder(this)
                .setTitle(R.string.select_device_title)
                .setNegativeButton(R.string.select_device_headset, { dialog, which -> setSelectedDevice(SelectedDevice.HEADSET) })
                .setPositiveButton(R.string.select_device_mikey, { dialog, which -> setSelectedDevice(SelectedDevice.MIKEY) })
                .setMessage(R.string.select_device_message)
                .setCancelable(false)
                .create()
        config = ConfigRepository(this)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        dialog.dismiss()
        overridePendingTransition(0, 0)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.getBooleanExtra(EXTRA_DISMISS, false) ?: false) {
            setSelectedDevice(SelectedDevice.UNDEFINED)
        }
    }

    private fun setSelectedDevice(selectedDevice: SelectedDevice) {
        ConfigRepository(this).setSelectedDevice(selectedDevice)

        if (selectedDevice != SelectedDevice.UNDEFINED) {
            HeadsetPluggedReceiver.notifyDevicePluggedIn(this, selectedDevice)
        }
        finish()
    }
}
