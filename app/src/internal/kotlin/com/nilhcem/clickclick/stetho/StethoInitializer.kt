package com.nilhcem.clickclick.stetho

import android.content.Context
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider

class StethoInitializer(private val context: Context) {

    fun init() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                        .build()
        )
    }
}
