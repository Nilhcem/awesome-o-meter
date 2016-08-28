package com.nilhcem.clickclick.test

import com.nilhcem.clickclick.core.utils.Realms
import com.nilhcem.clickclick.core.utils.toDate
import com.nilhcem.clickclick.model.app.dashboard.DateRange
import com.nilhcem.clickclick.model.realm.Click
import com.nilhcem.clickclick.repository.ClickRealmDataSource
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.util.*

object TestDataInitializer {

    fun fillWithTestData() {
        val clickDs = ClickRealmDataSource()
        if (clickDs.count() == 0L) {
            Timber.i("Fill with test data")

            Realms.executeTransaction { realm ->
                val random = Random()
                (0..(DateRange.THREE_MONTHS.nbDays - 1)).forEach { days ->
                    val awesomeToday = random.nextInt(3) == 0

                    if (awesomeToday) {
                        (0..(random.nextInt(13) + 1)).forEach {
                            val click = realm.createObject(Click::class.java)
                            click.date = LocalDate.now().minusDays(days.toLong()).toDate()
                        }
                    }
                }
            }
        }
    }
}
