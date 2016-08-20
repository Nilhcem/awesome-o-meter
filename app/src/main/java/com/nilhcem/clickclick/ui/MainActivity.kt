package com.nilhcem.clickclick.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.repository.ClickRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = ClickRepository()
        repo.insert()

        // Find the first click and read a field
        Log.e("MAIN", "NB CLICKS: ${repo.getTotal()}")
    }
}
