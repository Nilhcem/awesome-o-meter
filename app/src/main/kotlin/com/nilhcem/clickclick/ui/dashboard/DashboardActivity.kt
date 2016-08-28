package com.nilhcem.clickclick.ui.dashboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.model.app.dashboard.DashboardData
import kotlinx.android.synthetic.main.dashboard.*

class DashboardActivity : AppCompatActivity(), DashboardMvp.View {

    private val presenter: DashboardMvp.Presenter = DashboardPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        presenter.onCreate(this, savedInstanceState)
    }

    override fun getContext() = this

    override fun setDashboardData(data: DashboardData) {
        todayCount.text = data.todayCount.toString()
        totalCount.text = data.totalCount.toString()
    }
}
