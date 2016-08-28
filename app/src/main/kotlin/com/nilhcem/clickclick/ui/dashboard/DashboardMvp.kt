package com.nilhcem.clickclick.ui.dashboard

import android.content.Context
import android.os.Bundle
import com.nilhcem.clickclick.model.app.dashboard.DashboardData

interface DashboardMvp {

    interface View {
        fun getContext(): Context
        fun setDashboardData(data: DashboardData)
    }

    interface Presenter {
        fun onCreate(view: View, savedInstanceState: Bundle?)
        fun onRefreshDashboard(view: DashboardMvp.View)
    }
}
