package com.nilhcem.clickclick.model.app.dashboard

import com.github.mikephil.charting.data.Entry

data class DashboardData(val todayCount: Long, val totalCount: Long, val chartData: List<Entry>, val chartAxisStr: List<String>)
