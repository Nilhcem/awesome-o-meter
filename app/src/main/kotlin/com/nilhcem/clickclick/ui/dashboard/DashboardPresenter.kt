package com.nilhcem.clickclick.ui.dashboard

import android.content.Context
import android.os.Bundle
import com.nilhcem.clickclick.repository.ClickRepository
import com.nilhcem.clickclick.service.MiKeyService

class DashboardPresenter : DashboardMvp.Presenter {

    private val clickRepo = ClickRepository()

    override fun onCreate(view: DashboardMvp.View, savedInstanceState: Bundle?) {
        MiKeyService.start(view as Context)
        view.setDashboardData(clickRepo.getDashboardData())
    }
}
