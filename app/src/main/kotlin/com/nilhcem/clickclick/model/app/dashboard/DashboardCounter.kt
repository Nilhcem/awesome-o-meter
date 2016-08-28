package com.nilhcem.clickclick.model.app.dashboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.nilhcem.clickclick.R
import kotlinx.android.synthetic.main.dashboard_counter.view.*

class DashboardCounter : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.dashboard_counter, this, true)

        if (attrs != null) {
            val attrsArray = intArrayOf(android.R.attr.text)
            val typedArray = context.obtainStyledAttributes(attrs, attrsArray)
            dashboardText.text = typedArray.getText(0)
            typedArray.recycle()
        }
    }

    fun setCount(count: Long) {
        dashboardCounter.text = count.toString()
    }
}
