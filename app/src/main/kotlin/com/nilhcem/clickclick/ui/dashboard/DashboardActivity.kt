package com.nilhcem.clickclick.ui.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.AxisValueFormatter
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.model.app.dashboard.DashboardData
import com.nilhcem.clickclick.model.app.dashboard.DateRange
import kotlinx.android.synthetic.main.dashboard.*

class DashboardActivity : AppCompatActivity(), DashboardMvp.View {

    companion object {
        private val STATE_DATE_RANGE = "selectedDateRange"
        private val BROADCAST_EVENT_NAME = "clickEvent"

        fun notifyClickReceived(context: Context) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(BROADCAST_EVENT_NAME))
        }
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
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                presenter.setDateRange(this@DashboardActivity, DateRange.values()[position])
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        initChart()

        presenter.onCreate(this, savedInstanceState)
        presenter.setDateRange(this, if (savedInstanceState == null) DateRange.ONE_WEEK else DateRange.values()[savedInstanceState.getInt(STATE_DATE_RANGE)])
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(clickReceiver, IntentFilter(BROADCAST_EVENT_NAME))
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefreshDashboard(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_DATE_RANGE, presenter.getDateRange().ordinal)
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
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val intValue = value.toInt()
                if (intValue >= data.chartAxisStr.size) {
                    return ""
                }
                return data.chartAxisStr[value.toInt()]
            }

            override fun getDecimalDigits() = 0
        }

        with(LineDataSet(data.chartData, null)) {
            color = ContextCompat.getColor(this@DashboardActivity, R.color.colorPrimary)
            lineWidth = 4f
            setCircleColor(color)
            setDrawCircleHole(false)
            circleRadius = 2f
            setDrawValues(false)
            chart.data = LineData(this)
        }

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
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM_INSIDE
            }

            with(axisLeft) {
                isEnabled = true
                setDrawAxisLine(false)
                gridColor = ContextCompat.getColor(this@DashboardActivity, R.color.chartGridColor)
                gridLineWidth = 3f
                granularity = 1f
                textColor = ContextCompat.getColor(this@DashboardActivity, R.color.colorPrimaryDark)
                textSize = 12f
                setAxisMinValue(0f)
            }
        }
    }
}
