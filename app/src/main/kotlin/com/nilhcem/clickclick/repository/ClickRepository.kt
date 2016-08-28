package com.nilhcem.clickclick.repository

import com.github.mikephil.charting.data.Entry
import com.nilhcem.clickclick.core.utils.toDate
import com.nilhcem.clickclick.model.app.dashboard.DashboardData
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class ClickRepository() {

    private val realm = ClickRealmDataSource()

    fun insert() {
        realm.insert(Date())
    }

    fun getDashboardData(nbDays: Int, dateFormatterWeek: DateTimeFormatter, dateFormatterOther: DateTimeFormatter): DashboardData {
        // For each day, get a map [LocalDate][nb clicks]
        val today = LocalDate.now()
        val realmCountPerDay = realm.countPerDay(today.minusDays(nbDays.toLong()).toDate())
        val countPerDay = (0..nbDays - 1).reversed()
                .map { today.minusDays(it.toLong()) }
                .associateBy({ it }, { realmCountPerDay.getOrElse(it, { 0 }) })

        // Get a list of Entry(index, nb clicks)
        val chartData = countPerDay.entries.mapIndexed { index, entry ->
            Entry(index.toFloat(), entry.value.toFloat())
        }

        // Get a list of formatted time
        val chartAxisStr = countPerDay
                .map { it.key }
                .mapIndexed { index, localDate ->
                    localDate.format(if (index > nbDays - 8) dateFormatterWeek else dateFormatterOther)
                }

        // Get all clicks count for today
        val clicksToday = realm.count(LocalDate.now().toDate())

        // Get all clicks count
        val clicksTotal = realm.count()

        return DashboardData(clicksToday, clicksTotal, chartData, chartAxisStr)
    }
}
