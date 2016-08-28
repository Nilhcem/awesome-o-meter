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

    fun getCount(from: Date? = null, to: Date? = null) = execute { realm ->
        val where = realm.where(Click::class.java)
        if (from != null) {
            where.greaterThanOrEqualTo(Click.FIELD_DATE, from)
        }
        if (to != null) {
            where.lessThanOrEqualTo(Click.FIELD_DATE, to)
        }
        where.count()
    }
}
