package com.example.smseasyviewer.utils

import android.util.Log

fun getPriorData(textBody: String, dataSet: HashMap<String, Int>) {
    val paymentPattern = Regex("Oplata(.*)")
    val pattern = Regex("(.*)Oplata(.*)Dostupno(.*)")
    if (textBody.matches(pattern)) {
        val twoStrings = textBody.trim().split("BYN. BLR")
        if (twoStrings.size == 2) {
            val location = twoStrings[1].split("Dostupno")
            if (textBody.trim().contains("WWW", true)) {
                Log.d("PATTERN", "$twoStrings")
            }
            val sum = paymentPattern.find(twoStrings[0])
            val value = sum?.value.toString().replace("Oplata", "").trim()
            val intValue = value.split(".")[0].toInt()
            val locationName = ConvertLocationName(location[0])

            if (locationName.trim().contains("WWW", true)) {
                Log.d("PATTERN", "$textBody")
            }
            if (intValue >= 1) {
                if (dataSet.containsKey(locationName)) {
                    dataSet[locationName] = dataSet[locationName]!! + intValue
                } else {
                    dataSet[locationName] = intValue
                }
            }
        }
    }
}
