package com.nilhcem.clickclick

import com.nilhcem.clickclick.stetho.StethoInitializer
import com.nilhcem.clickclick.test.TestDataInitializer

class InternalApp : App() {

    override fun onCreate() {
        super.onCreate()
        StethoInitializer(this).init()

        // For testing purposes
        TestDataInitializer.fillWithTestData()
    }
}
