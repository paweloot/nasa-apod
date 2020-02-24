package com.paweloot.nasaapod.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun buildLastWeekDates(): List<String> {
        val dateList = mutableListOf<String>()
        val today = Calendar.getInstance().time

        for (i in 1..7) {
            dateList.add(
                SimpleDateFormat(DateConstants.apodDateFormat, Locale.getDefault()).format(today)
            )

            today.time = today.time - DateConstants.dayInMillis
        }

        return dateList
    }
}