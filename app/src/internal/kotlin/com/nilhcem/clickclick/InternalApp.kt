package com.nilhcem.clickclick

import com.nilhcem.clickclick.stetho.StethoInitializer

class InternalApp : App() {

    override fun onCreate() {
        super.onCreate()
        StethoInitializer(this).init()
    }
}
