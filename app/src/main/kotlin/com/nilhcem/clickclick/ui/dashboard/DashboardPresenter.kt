package com.nilhcem.clickclick.ui.dashboard

import android.content.Context
import android.os.Bundle
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.repository.ClickRepository
import com.nilhcem.clickclick.service.MiKeyService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber

class DashboardPresenter : DashboardMvp.Presenter {

    private val clickRepo = ClickRepository()

    override fun onCreate(view: DashboardMvp.View, savedInstanceState: Bundle?) {
        MiKeyService.start(view as Context)
    }

    override fun onRefreshDashboard(view: DashboardMvp.View) {
        doAsync {
            try {
                val ctx = view.getContext()
                val dateFormatterWeek = DateTimeFormatter.ofPattern(ctx.getString(R.string.dashboard_chart_date_format_week))
                val dateFormatterOther = DateTimeFormatter.ofPattern(ctx.getString(R.string.dashboard_chart_date_format_other))
                val data = clickRepo.getDashboardData(7, dateFormatterWeek, dateFormatterOther)

                uiThread {
                    view.setDashboardData(data)
                }
            } catch (e: Exception) {
                Timber.e(e, "Error getting data")
            }
        }
    }
}
