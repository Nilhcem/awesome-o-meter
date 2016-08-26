package com.nilhcem.clickclick.core.utils

import android.content.Context
import android.media.AudioManager
import android.os.Build
import java.lang.reflect.Method

object AudioManagerUtils {

    fun getAudioManager(context: Context) = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    /**
     * @param device type of device connected/disconnected (AudioManager.DEVICE_OUT_xxx)
     * @param state new connection state: 1 connected, 0 disconnected
     * @param name device name
     */
    fun setWiredDeviceConnectionState(context: Context, device: Int, state: Int, name: String = "", address: String = "") {
        val method: Method
        val audioManager = getAudioManager(context)

        // We are forced to use reflection to route audio
        // http://stackoverflow.com/questions/38461743/how-to-route-default-audio-to-ear-piece-when-headphones-are-connected
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            method = AudioManager::class.java.getMethod("setWiredDeviceConnectionState", *arrayOf(Integer.TYPE, Integer.TYPE, String::class.java, String::class.java))
            method.isAccessible = true
            method.invoke(audioManager, *arrayOf(device, state, address, name))
        } else {
            method = AudioManager::class.java.getMethod("setWiredDeviceConnectionState", *arrayOf(Integer.TYPE, Integer.TYPE, String::class.java))
            method.isAccessible = true
            method.invoke(audioManager, *arrayOf(device, state, name))
        }
    }
}
