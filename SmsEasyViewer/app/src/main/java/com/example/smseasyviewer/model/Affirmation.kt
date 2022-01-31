package com.example.happybirthday.model

import androidx.annotation.StringRes

data class SmsData (val name : String, val value: Int) {
}

class Affirmation (@StringRes val stringResourceId: Int) {
}
