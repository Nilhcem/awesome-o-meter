package com.nilhcem.clickclick.repository

import com.nilhcem.clickclick.model.app.dashboard.DashboardData
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import java.util.*

class ClickRepository() {

    private val realm = ClickRealmDataSource()

    fun insert() {
        realm.insert(Date())
    }

    fun getDashboardData(): DashboardData {
        return DashboardData(getCountToday(), realm.getCount())
    }

    private fun getCountToday() = realm.getCount(Date(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()))
}
