package com.nilhcem.clickclick.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.repository.ClickRepository
import com.nilhcem.clickclick.service.MiKeyService
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = ClickRepository()
        repo.insert()

        // Find the first click and read a field
        Timber.e("NB CLICKS: ${repo.getTotal()}")

        MiKeyService.start(this)
    }
}
