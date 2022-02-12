package com.example.smseasyviewer.utils

import android.util.Log

fun ConvertLocationName(location: String): String {
    var rezString = location
        .replace("VITEBSK", "", true)
        .replace("MINSK", "", true)
        .replace("BPS", "", true)
        .replace("BLR", "", true)
        .replace("BAPB", "", true)
        .replace("PT", "", true)
        .replace("SHOP", "shop")
        .replace("MED", "med")
        .replace("OOO", "", true)
        .replace("MAGAZIN", "magazin")
        .replace("\"", "", true)
    if (location.isNotEmpty() && location.uppercase().contains("VEST", true)) {
        return "VESTA";
    }
    if (location.isNotEmpty() && location.uppercase().contains("AZS", true)) {
        return "BENZIN"
    }
    if (location.isNotEmpty() && location.uppercase().contains("EVROOPT", true)) {
        return "EVROOPT"
    }
    if (location.isNotEmpty() && location.uppercase().contains("MATERIK", true)) {
        return "MATERIK"
    }
    if (location.isNotEmpty() && location.uppercase().contains("KORONA", true)) {
        return "KORONA"
    }
    if (location.isNotEmpty() && location.uppercase().contains("VITALUR", true)) {
        return "VITALUR"
    }
    if (location.isNotEmpty() && location.uppercase().contains("APTEKA", true)) {
        return "APTEKA"
    }
    if (location.isNotEmpty() && location.uppercase().contains("GREEN", true)) {
        return "GREEN"
    }
    if (location.isNotEmpty() && location.uppercase().contains("GIPPO", true)) {
        return "GIPPO"
    }
    if (location.isNotEmpty() && location.uppercase().contains("ONLAJN-GIP", true)) {
        return "ONLAJN 21VEK"
    }
    if (location.isNotEmpty() && location.uppercase().contains("ZOO", true)) {
        return "ZOO"
    }
    if (location.isNotEmpty() && (location.uppercase().contains("MEGAHEND", true)
                || location.uppercase().contains("MEGAKHEND", true))
    ) {
        return "MEGAHEND"
    }
    ///////////////
//    if (rezString.trim().contains("WWW", true)) {
//        Log.d("PATTERN", "$location")
//    }
    /////////////////////
    return rezString.trim()
}
