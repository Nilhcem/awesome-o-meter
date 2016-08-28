package com.nilhcem.clickclick

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initRealm()
        initThreeTen()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initRealm() {
        val realmConfig = RealmConfiguration.Builder(this).build()
        Realm.setDefaultConfiguration(realmConfig)
    }

    private fun initThreeTen() {
        AndroidThreeTen.init(this)
    }
}
