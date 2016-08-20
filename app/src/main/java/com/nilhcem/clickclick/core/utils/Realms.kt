package com.nilhcem.clickclick.core.utils

import io.realm.Realm

object Realms {

    fun <T> execute(toExec: (Realm) -> T): T {
        Realm.getDefaultInstance().use { return toExec(it) }
    }

    fun executeTransaction(toExec: (Realm) -> Unit) = execute {
        it.executeTransaction(toExec)
    }
}
