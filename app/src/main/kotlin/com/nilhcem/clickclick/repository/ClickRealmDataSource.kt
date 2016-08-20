package com.nilhcem.clickclick.repository

import com.nilhcem.clickclick.core.utils.Realms.execute
import com.nilhcem.clickclick.core.utils.Realms.executeTransaction
import com.nilhcem.clickclick.model.realm.Click
import java.util.*

/* Do not instantiate this elsewhere from ClickRepository */
class ClickRealmDataSource {

    fun insert(date: Date) = executeTransaction { realm ->
        val click = realm.createObject(Click::class.java)
        click.date = date
    }

    fun getTotal() = execute { realm ->
        realm.where(Click::class.java).count()
    }
}
