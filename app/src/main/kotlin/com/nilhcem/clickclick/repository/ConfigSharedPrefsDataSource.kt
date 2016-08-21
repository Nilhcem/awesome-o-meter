package com.nilhcem.clickclick.repository

import android.content.Context
import android.preference.PreferenceManager
import com.nilhcem.clickclick.model.app.SelectedDevice

/* Do not instantiate this elsewhere from ConfigRepository */
class ConfigSharedPrefsDataSource(context: Context) {

    companion object {
        val KEY_SELECTED_DEVICE = "selected"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getSelectedDevice(): SelectedDevice {
        val id = prefs.getInt(KEY_SELECTED_DEVICE, SelectedDevice.UNDEFINED.ordinal)
        return SelectedDevice.values()[id]
    }

    fun setSelectedDevice(selected: SelectedDevice) {
        prefs.edit().putInt(KEY_SELECTED_DEVICE, selected.ordinal).apply()
    }
}
