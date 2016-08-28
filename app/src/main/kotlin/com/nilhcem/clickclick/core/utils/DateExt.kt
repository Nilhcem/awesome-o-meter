package com.nilhcem.clickclick.core.utils

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import java.util.*

fun Date.toLocalDate() = DateTimeUtils.toInstant(this).atZone(ZoneId.systemDefault()).toLocalDate()
fun LocalDate.toDate() = DateTimeUtils.toDate(atStartOfDay().toInstant(ZoneOffset.UTC))
