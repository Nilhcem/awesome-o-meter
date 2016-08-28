package com.nilhcem.clickclick.ui.dashboard

import android.content.Context
import android.os.Bundle
import com.nilhcem.clickclick.repository.ClickRepository
import com.nilhcem.clickclick.service.MiKeyService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DashboardPresenter : DashboardMvp.Presenter {

    private val clickRepo = ClickRepository()

    override fun onCreate(view: DashboardMvp.View, savedInstanceState: Bundle?) {
        MiKeyService.start(view as Context)
    }

    override fun onRefreshDashboard(view: DashboardMvp.View) {
        doAsync {
            val data = clickRepo.getDashboardData()
            uiThread {
                view.setDashboardData(data)
            }
        }
    }
}
