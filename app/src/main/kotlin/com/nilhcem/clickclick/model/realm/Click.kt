package com.nilhcem.clickclick.model.realm

import io.realm.RealmObject
import io.realm.annotations.Required
import java.util.*

open class Click : RealmObject() {

    companion object {
        val FIELD_DATE = "date"
    }

    @Required open lateinit var date: Date
}
