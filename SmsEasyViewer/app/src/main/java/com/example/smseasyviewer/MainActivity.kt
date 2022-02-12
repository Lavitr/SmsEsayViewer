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
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.smseasyviewer.utils.readSms
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    var fromDate: Long = 0L
    var toDate: Long = System.currentTimeMillis()
    var bankID = 0
    var fromDateString = ""
    var dateToday = LocalDateTime.now().toLocalDate().toString()
    var toDateString = ""

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

        val radioButton1 = findViewById<RadioButton>(R.id.radio_button_1)
        val radioButton2 = findViewById<RadioButton>(R.id.radio_button_2)

        radioButton1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) bankID = 0
        }
        radioButton2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) bankID = 1
        }
        //datePicker
        val myCalendar = Calendar.getInstance()
        val outlinedTextField = findViewById<TextInputLayout>(R.id.outlinedTextField)
        val outlinedTextField2 = findViewById<TextInputLayout>(R.id.outlinedTextField2)
        outlinedTextField.editText?.showSoftInputOnFocus = false
        outlinedTextField2.editText?.showSoftInputOnFocus = false


        fun datePickerListener(isFrom: Boolean) =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val text = DateFormat.getDateInstance().format(myCalendar.time)
                if (isFrom) {
                    fromDate = myCalendar.getTimeInMillis();
                    outlinedTextField.editText?.setText(text)
                    fromDateString = text
                } else {
                    toDate = myCalendar.getTimeInMillis()
                    outlinedTextField2.editText?.setText(text)
                    toDateString = text
                }
            }

        outlinedTextField.editText?.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, datePickerListener(true),
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
        outlinedTextField2.editText?.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, datePickerListener(false),
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        outlinedTextField.setEndIconOnClickListener {
            fromDate = 0L
            fromDateString = ""
            outlinedTextField.editText?.setText("")
        }
        outlinedTextField2.setEndIconOnClickListener {
            toDate = System.currentTimeMillis()
            toDateString = ""
            outlinedTextField2.editText?.setText("")
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val projection = arrayOf("address", "body", "type", "date")
            val cursor = contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                projection,
                "date>=$fromDate and date<=$toDate",
                null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
            )
            val (data, count, sum) = readSms(cursor, bankID)
            Intent(this, ResultViewActivity::class.java).also {
                it.putExtra("hashMap", data)
                it.putExtra("count", count)
                it.putExtra("from", fromDateString)
                it.putExtra("to", toDateString)
                it.putExtra("bankID", bankID)
                it.putExtra("sum", sum)
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
