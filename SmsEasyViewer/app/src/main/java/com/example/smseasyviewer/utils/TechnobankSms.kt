package com.example.smseasyviewer.utils

import android.util.Log

fun getTechnobankData(textBody: String, dataSet: HashMap<String, Int>) {
    val paymentPattern = Regex("Retail(.*)")
    val pattern = Regex("(.*)Retail(.*)")
    if (textBody.matches(pattern)) {
        val twoStrings = textBody.trim().split("BYN ")
        if (twoStrings.size == 2) {
            var location = twoStrings[1].split("OK.")[0]
            location = ConvertLocationName(location)
            val sum = paymentPattern.find(twoStrings[0])
            val value = sum?.value.toString().replace("Retail", "").replace("-", "").trim()
            val intValue = value.split(".")[0].toInt()
            if (dataSet.containsKey(location)) {
                dataSet[location] = dataSet[location]!! + intValue
            } else {
                dataSet[location] = intValue
            }
        }
    }
}
