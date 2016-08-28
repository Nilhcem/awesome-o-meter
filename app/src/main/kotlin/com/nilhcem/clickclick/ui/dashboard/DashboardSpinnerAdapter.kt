package com.nilhcem.clickclick.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nilhcem.clickclick.R
import com.nilhcem.clickclick.model.app.dashboard.TimePeriod
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class DashboardSpinnerAdapter : BaseAdapter {

    private val values: List<CharSequence>

    constructor(context: Context) {
        val today = LocalDate.now()
        val dateFormat = DateTimeFormatter.ofPattern(context.getString(R.string.dashboard_spinner_date_format))
        val todayStr = today.format(dateFormat)
        val spanBlack = ForegroundColorSpan(Color.parseColor("#404040"))
        val spanBold = StyleSpan(Typeface.BOLD)

        values = TimePeriod.values().map {
            val daysStr = context.getString(R.string.dashboard_spinner_content_first, it.nbDays)

            val sb = SpannableStringBuilder()
            sb.append(daysStr)
            sb.append(context.getString(R.string.dashboard_spinner_content_second, today.minusDays((it.nbDays - 1).toLong()).format(dateFormat), todayStr))
            sb.setSpan(spanBlack, 0, daysStr.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            sb.setSpan(spanBold, 0, daysStr.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            sb
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = if (convertView == null) {
            LayoutInflater.from(parent.context).inflate(R.layout.dashboard_spinner_item, parent, false) as TextView
        } else {
            convertView as TextView
        }

        view.text = getItem(position)
        return view
    }

    override fun getItem(position: Int) = values[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = TimePeriod.values().size
}
