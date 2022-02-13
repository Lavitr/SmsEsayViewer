package com.example.smseasyviewer.utils

import android.database.Cursor
import android.provider.Telephony
import android.util.Log

data class Result(val data: HashMap<String, Int>, val count: Int, val sum: Int)

fun readSms(cursor: Cursor?, bankID: Int): Result {
    var sum = 0
    val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
    val textCol = Telephony.TextBasedSmsColumns.BODY
    val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent
    val dateCol = Telephony.TextBasedSmsColumns.DATE

    val numberColIdx = cursor!!.getColumnIndex(numberCol)
    val textColIdx = cursor.getColumnIndex(textCol)
    val typeColIdx = cursor.getColumnIndex(typeCol)
    val dateColIdx = cursor.getColumnIndex(dateCol)
    var count = 0
    val dataSet = HashMap<String, Int>()

    while (cursor.moveToNext()) {
        val sender = cursor.getString(numberColIdx)
        val textBody = cursor.getString(textColIdx)
        val type = cursor.getString(typeColIdx)
        val date = cursor.getString(dateColIdx)
        if (sender == "Technobank" && type == "1" && bankID == 1) {
//            Log.d("Technobank::$sender ", "$textBody --- $date")
            count += getTechnobankData(textBody, dataSet)
        } else if (sender == "Priorbank" && type == "1" && bankID == 0) {
//            Log.d("Priorbank::$sender ", "$textBody")
            count += getPriorData(textBody, dataSet)
        }
    }
    dataSet.values.forEach { value -> sum = sum + value }
    return Result(dataSet, count, sum)
};

