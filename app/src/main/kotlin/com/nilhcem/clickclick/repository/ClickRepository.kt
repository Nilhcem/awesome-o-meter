package com.nilhcem.clickclick.repository

import java.util.*

class ClickRepository() {

    private val realm = ClickRealmDataSource()

    fun insert() {
        realm.insert(Date())
    }

    fun getTotal(): Long {
        return realm.getTotal()
    }
}
