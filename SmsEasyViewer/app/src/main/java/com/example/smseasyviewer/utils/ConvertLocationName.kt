package com.example.smseasyviewer.utils

fun ConvertLocationName(location: String): String {
    var rezString = location
        .replace("SHOP", "", true)
        .replace("VITEBSK", "", true)
        .replace("MINSK", "", true)
        .replace("BPS", "", true)
        .replace("BLR", "", true)
        .replace("BAPB", "", true)
    return rezString
}
