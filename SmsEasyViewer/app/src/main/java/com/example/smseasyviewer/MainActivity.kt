package com.example.smseasyviewer

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.icu.util.Calendar
import android.os.Build
import android.icu.text.DateFormat
import android.provider.Telephony
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import com.example.smseasyviewer.utils.readSms

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
        //cursor
        val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
        val textCol = Telephony.TextBasedSmsColumns.BODY
        val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent
        val projection = arrayOf(numberCol, textCol, typeCol)
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        )
        //cursor

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val data = readSms(cursor)
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
}
