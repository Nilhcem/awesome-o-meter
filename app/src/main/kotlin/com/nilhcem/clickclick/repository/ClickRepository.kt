package com.nilhcem.clickclick.repository

import com.github.mikephil.charting.data.Entry
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
        // TODO: Use real data
        val chartData = listOf(Entry(0f, 2f), Entry(1f, 1f), Entry(2f, 3f), Entry(3f, 3f), Entry(4f, 4f), Entry(5f, 0f), Entry(6f, 3f))
        val chartAxisStr = listOf("Fri", "Sat", "Sun", "Mon", "Tue", "Wed", "Thu")

        return DashboardData(getCountToday(), realm.getCount(), chartData, chartAxisStr)
    }

    private fun getCountToday() = realm.getCount(Date(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()))
}
