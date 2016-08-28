package com.nilhcem.clickclick.ui.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.AxisValueFormatter
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
        spinner.adapter = DashboardSpinnerAdapter(this)
        initChart()
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

        chart.xAxis.valueFormatter = object : AxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?) = data.chartAxisStr[value.toInt()]
            override fun getDecimalDigits() = 0
        }

        val lineDataSet = LineDataSet(data.chartData, null)
        lineDataSet.color = ContextCompat.getColor(this, R.color.colorPrimary)
        lineDataSet.lineWidth = 4f
        lineDataSet.setCircleColor(lineDataSet.color)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.circleRadius = 2f
        lineDataSet.setDrawValues(false)

        chart.data = LineData(lineDataSet)
        chart.notifyDataSetChanged()
        chart.invalidate()
    }


    private fun initChart() {
        with(chart) {
            legend.isEnabled = false
            setTouchEnabled(false)
            setDescription(null)
            setDrawGridBackground(false)
            setDrawBorders(false)
            axisRight.isEnabled = false

            with(xAxis) {
                isEnabled = true
                setDrawAxisLine(false)
                setDrawGridLines(false)
                granularity = 1.0f
                position = XAxis.XAxisPosition.BOTTOM_INSIDE
            }

            with(axisLeft) {
                isEnabled = true
                setDrawAxisLine(false)
                gridColor = ContextCompat.getColor(this@DashboardActivity, R.color.chartGridColor)
                gridLineWidth = 3.0f
                granularity = 1.0f
                textColor = ContextCompat.getColor(this@DashboardActivity, R.color.colorPrimaryDark)
                textSize = 12.0f
            }
        }
    }
}
