package com.example.smseasyviewer

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import android.widget.DatePicker
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.happybirthday.adapter.ItemAdapter
import com.example.smseasyviewer.data.Datasource
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

        /////////////////////////
        val myDataset = Datasource().loadAffirmations()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)
        /////////////////////////

        val textView = findViewById<TextView>(R.id.text)
        val textView2 = findViewById<TextView>(R.id.text2)
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

        textView.text = "Huj them all!!"

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            readSms(textView, textView2)
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

    private fun readSms(text1: TextView, text2: TextView) {
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

        while (cursor.moveToNext() && i < 10) {
            val number = cursor.getString(numberColIdx)
            val textBody = cursor.getString(textColIdx)
            val type = cursor.getString(typeColIdx)

            Log.d("MY_APP::$i", "$number :: $textBody :: $type")
            i++
            if (i == 9) {
                text2.text = number
                text1.text = textBody
            }
        }

        cursor.close()
    }
}
