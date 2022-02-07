package com.example.smseasyviewer.data

import SmsData

class Datasource {
    fun loadSmsData(): List<SmsData> {
        return listOf<SmsData>(
            SmsData("affirmation1", 22),
            SmsData("affirmation1", 22),
            SmsData("affirmation1", 242),
            SmsData("affirmation1", 223),
            SmsData("affirmation1", 222),
            SmsData("affirmation1", 25),
            SmsData("affirmation1", 225),
            SmsData("affirmation1", 225),
            SmsData("affirmation1", 225),
            SmsData("affirmation1", 223),

        )
    }
}