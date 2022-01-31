package com.example.smseasyviewer

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.icu.util.Calendar
import android.os.Build
import android.icu.text.DateFormat
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("after", "!! After permission Granted!!!")
            } else {
                Log.d("after", "After not!1 permission Granted")

            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //datePicker
        val myCalendar = Calendar.getInstance()
        val outlinedTextField = findViewById<TextInputLayout>(R.id.outlinedTextField)
        val outlinedTextField2 = findViewById<TextInputLayout>(R.id.outlinedTextField2)

        val datePickerListener =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val text = DateFormat.getDateInstance().format(myCalendar.time)
                outlinedTextField.editText?.setText(text)
            }

        val datePickerListener1 =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val text = DateFormat.getDateInstance().format(myCalendar.time)
                outlinedTextField2.editText?.setText(text)
            }

        outlinedTextField.editText?.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, datePickerListener,
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
        outlinedTextField2.editText?.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, datePickerListener1,
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val data = readSms()
            Intent(this, ResultViewActivity::class.java).also {
                it.putExtra("hashMap", data)
                startActivity(it)
            }
        }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_SMS
            ) -> {
                Log.d("after", "After permission Granted444")
            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.READ_SMS
                )
            }
        }
    }

    private fun readSms(): HashMap<String, Int> {
        val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
        val textCol = Telephony.TextBasedSmsColumns.BODY
        val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent

        val projection = arrayOf(numberCol, textCol, typeCol)

        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        )

        val numberColIdx = cursor!!.getColumnIndex(numberCol)
        val textColIdx = cursor.getColumnIndex(textCol)
        val typeColIdx = cursor.getColumnIndex(typeCol)
        var i = 0
        val dataSet = HashMap<String, Int>()
        while (cursor.moveToNext() && i < 30) {
            val sender = cursor.getString(numberColIdx)
            val textBody = cursor.getString(textColIdx)
            val type = cursor.getString(typeColIdx)

            Log.d("MY_APP::$i", "$sender :: $textBody :: $type")
            dataSet[textBody.trim().slice(0..30)] = i
            i++
        }

//        Log.d("MY_APP::dataSet", dataSet.toString())
        cursor.close()
        return dataSet
    }
}
