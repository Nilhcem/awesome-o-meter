package com.nilhcem.clickclick

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        val realmConfig = RealmConfiguration.Builder(this).build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}
