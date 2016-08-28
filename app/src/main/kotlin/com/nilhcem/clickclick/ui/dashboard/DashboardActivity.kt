package com.nilhcem.clickclick.ui.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.model.app.dashboard.DashboardData
import kotlinx.android.synthetic.main.dashboard.*

class DashboardActivity : AppCompatActivity(), DashboardMvp.View {

    companion object {
        private val BROADCAST_EVENT_NAME = "clickEvent"

        fun notifyClickReceived(context: Context) =
                LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(BROADCAST_EVENT_NAME))
    }

    private val presenter: DashboardMvp.Presenter = DashboardPresenter()
    private val clickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.onRefreshDashboard(this@DashboardActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        presenter.onCreate(this, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(clickReceiver, IntentFilter(BROADCAST_EVENT_NAME))
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefreshDashboard(this)
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(clickReceiver)
        super.onStop()
    }

    override fun getContext() = this

    override fun setDashboardData(data: DashboardData) {
        todayCount.text = data.todayCount.toString()
        totalCount.text = data.totalCount.toString()
    }
}
