package com.example.smseasyviewer.utils

import android.util.Log

fun getPriorData(textBody: String, dataSet: HashMap<String, Int>) {
    val paymentPattern = Regex("Oplata(.*)")
    val pattern = Regex("(.*)Oplata(.*)Dostupno(.*)")
    if (textBody.matches(pattern)) {
        Log.d("MY_APP::", "$textBody")
        val twoStrings = textBody.trim().split("BYN. BLR")
        if (twoStrings.size == 2) {
            val location = twoStrings[1].split(".")
            val sum = paymentPattern.find(twoStrings[0])
            val value = sum?.value.toString().replace("Oplata", "").trim()
            val intValue = value.split(".")[0].toInt()
            if (dataSet.containsKey(location[0])) {
                dataSet[location[0]] = dataSet[location[0]]!! + intValue
            } else {
                dataSet[location[0]] = intValue
            }
        }
    }
}
