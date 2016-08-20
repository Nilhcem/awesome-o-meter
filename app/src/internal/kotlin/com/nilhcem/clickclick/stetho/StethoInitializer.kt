package com.nilhcem.clickclick.stetho

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.timber.StethoTree
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import timber.log.Timber

class StethoInitializer(private val context: Context) {

    fun init() {
        Timber.plant(StethoTree())

        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                        .build()
        )
    }
}
