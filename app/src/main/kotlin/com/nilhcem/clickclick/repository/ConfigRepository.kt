package com.nilhcem.clickclick.repository

import android.content.Context
import com.nilhcem.clickclick.model.app.SelectedDevice

class ConfigRepository(context: Context) {

    private val prefs = ConfigSharedPrefsDataSource(context)

    fun getSelectedDevice(): SelectedDevice {
        return prefs.getSelectedDevice()
    }

    fun setSelectedDevice(selected: SelectedDevice) {
        prefs.setSelectedDevice(selected)
    }
}
