package com.nilhcem.clickclick.ui.headset

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nilhcem.clickclick.receiver.MediaButtonReceiver

class LongClickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MediaButtonReceiver.receiveLongClick(this)
        finish()
    }
}
