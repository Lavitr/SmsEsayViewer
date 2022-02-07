package com.example.smseasyviewer

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.DateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.smseasyviewer.utils.readSms
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
    private val dateFrom: TextInputLayout? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //datePicker
        val myCalendar = Calendar.getInstance()
        val outlinedTextField = findViewById<TextInputLayout>(R.id.outlinedTextField)
        val outlinedTextField2 = findViewById<TextInputLayout>(R.id.outlinedTextField2)

        fun datePickerListener1(textInput: TextInputLayout) =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val text = DateFormat.getDateInstance().format(myCalendar.time)
                textInput.editText?.setText(text)
            }

        outlinedTextField.editText?.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, datePickerListener1(outlinedTextField),
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
        outlinedTextField2.editText?.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, datePickerListener1(outlinedTextField2),
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val projection = arrayOf("address", "body", "type")
            val cursor = contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                projection, null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
            )
            val (data, count) = readSms(cursor)
            Intent(this, ResultViewActivity::class.java).also {
                it.putExtra("hashMap", data)
                it.putExtra("count", count)
                startActivity(it)
            }
            cursor!!.close()
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
