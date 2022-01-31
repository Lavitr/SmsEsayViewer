package com.example.smseasyviewer.data

import SmsData

class Datasource {
    fun loadSmsData(): List<SmsData> {
        return listOf<SmsData>(
            SmsData("affirmation1", 22.00f),
            SmsData("affirmation1", 22.00f),
            SmsData("affirmation1", 242.00f),
            SmsData("affirmation1", 223.00f),
            SmsData("affirmation1", 222.00f),
            SmsData("affirmation1", 25.00f),
            SmsData("affirmation1", 225.00f),
            SmsData("affirmation1", 225.00f),
            SmsData("affirmation1", 225.00f),
            SmsData("affirmation1", 223.00f),

        )
    }
}