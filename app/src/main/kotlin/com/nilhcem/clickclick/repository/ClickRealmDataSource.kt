package com.nilhcem.clickclick.repository

import com.nilhcem.clickclick.core.utils.Realms.execute
import com.nilhcem.clickclick.core.utils.Realms.executeTransaction
import com.nilhcem.clickclick.core.utils.toLocalDate
import com.nilhcem.clickclick.model.realm.Click
import io.realm.RealmQuery
import java.util.*

/* Do not instantiate this elsewhere from ClickRepository */
class ClickRealmDataSource {

    fun insert(date: Date) = executeTransaction { realm ->
        val click = realm.createObject(Click::class.java)
        click.date = date
    }

    fun countPerDay(from: Date? = null, to: Date? = null) = execute { realm ->
        realm.where(Click::class.java).between(from, to).findAllSorted(Click.FIELD_DATE)
                .map { it.date.toLocalDate() to 1 }
                .groupBy({ it.first }, { it.second })
                .map { it.key to it.value.size }
                .associateBy({ it.first }, { it.second })
    }

    fun count(from: Date? = null, to: Date? = null) = execute { realm ->
        realm.where(Click::class.java).between(from, to).count()
    }

    private fun RealmQuery<Click>.between(from: Date? = null, to: Date? = null): RealmQuery<Click> {
        if (from != null) {
            greaterThanOrEqualTo(Click.FIELD_DATE, from)
        }
        if (to != null) {
            lessThanOrEqualTo(Click.FIELD_DATE, to)
        }
        return this
    }
}
