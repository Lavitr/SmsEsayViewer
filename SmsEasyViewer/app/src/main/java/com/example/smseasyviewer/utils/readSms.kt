package com.example.smseasyviewer.utils

import android.database.Cursor
import android.provider.Telephony
import android.util.Log

data class Result(val data: HashMap<String, Int>, val count: Int)

fun readSms(cursor: Cursor?): Result {
    val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
    val textCol = Telephony.TextBasedSmsColumns.BODY
    val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent

    val numberColIdx = cursor!!.getColumnIndex(numberCol)
    val textColIdx = cursor.getColumnIndex(textCol)
    val typeColIdx = cursor.getColumnIndex(typeCol)
    var count = 0
    val dataSet = HashMap<String, Int>()

    while (cursor.moveToNext()) {
        val sender = cursor.getString(numberColIdx)
        val textBody = cursor.getString(textColIdx)
        val type = cursor.getString(typeColIdx)
        if (sender == "Technobank" && type == "1") {
            Log.d("Technobank::$sender ", "$textBody")
            getTechnobankData(textBody, dataSet)
            count++
        } else if (sender == "Priorbank" && type == "1") {
//            Log.d("Priorbank::$sender ", "$textBody")
//            getPriorData(textBody, dataSet)
//            count++
        }
    }
    dataSet["total"] = count.toInt();
    return Result(dataSet, count)
};

