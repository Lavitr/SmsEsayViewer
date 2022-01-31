package com.example.smseasyviewer.utils

import android.database.Cursor
import android.provider.Telephony
import android.util.Log

fun readSms(cursor: Cursor?): HashMap<String, Float> {
    val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
    val textCol = Telephony.TextBasedSmsColumns.BODY
    val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent

    val numberColIdx = cursor!!.getColumnIndex(numberCol)
    val textColIdx = cursor.getColumnIndex(textCol)
    val typeColIdx = cursor.getColumnIndex(typeCol)
    var i = 0
    val dataSet = HashMap<String, Float>()
    val paymentPattern = Regex("Oplata(.*)")
    val pattern = Regex("(.*)Oplata(.*)Dostupno(.*)")

    while (cursor.moveToNext()) {
        val sender = cursor.getString(numberColIdx)
        val textBody = cursor.getString(textColIdx)
        val type = cursor.getString(typeColIdx)
        if (sender == "Technobank") {
            Log.d("Technobank", "$sender :: $textBody :: $type")

        }
        if (sender == "Priorbank") {
            Log.d("Priorbank", "$sender :: $textBody :: $type")
            if (textBody.matches(pattern)) {
                Log.d("MY_APP::$i", "$textBody")
                val twoStrings = textBody.trim().split("BYN. BLR")
                if (twoStrings.size == 2) {
                    val location = twoStrings[1].split(".")
                    val sum = paymentPattern.find(twoStrings[0])
                    val value = sum?.value.toString().replace("Oplata", "").trim().toFloat()
                    if (dataSet.containsKey(location[0])) {
                        dataSet[location[0]] = dataSet[location[0]]!!.plus(value)
                    } else {
                        dataSet[location[0]] = value
                    }

                }

            }
        }

//        Log.d("MY_APP::$i", "$sender :: $textBody :: $type")
//        dataSet[textBody.trim().slice(0..10)] = i
        i++
    }
    dataSet["total sms number"] = i.toFloat();
    cursor.close()
    return dataSet
};

