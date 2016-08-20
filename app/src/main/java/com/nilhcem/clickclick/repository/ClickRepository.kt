package com.nilhcem.clickclick.repository

import java.util.*

class ClickRepository(val realm: ClickRealmDataSource = ClickRealmDataSource()) {

    fun insert() {
        realm.insert(Date())
    }

    fun getTotal(): Long {
        return realm.getTotal()
    }
}
