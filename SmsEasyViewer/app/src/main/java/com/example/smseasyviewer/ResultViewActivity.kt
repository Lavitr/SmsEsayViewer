package com.example.smseasyviewer

import SmsData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happybirthday.adapter.ItemAdapter
import com.example.smseasyviewer.data.Datasource

class ResultViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_view)

        val myDataset = intent.getSerializableExtra("hashMap") as HashMap<String, Int>
        /////get from intent
        val count = intent.getIntExtra("count", 0)
        val from = intent.getStringExtra("from")
        val to = intent.getStringExtra("to")
        val bankID = intent.getIntExtra("bankID", 1)
        val sum = intent.getIntExtra("sum", 0)

        val listData = myDataset.toList().sortedByDescending { (_, value) -> value }
            .map { SmsData(it.first, it.second) }
//        val listData = Datasource().loadSmsData()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, listData)
        recyclerView.setHasFixedSize(true)
        ///////////////////////// TEXT FIELD //////
        val textFrom = findViewById<TextView>(R.id.textViewFrom)
        val textTo = findViewById<TextView>(R.id.textViewTo)
        val bankName = findViewById<TextView>(R.id.bankName)
        val countSms = findViewById<TextView>(R.id.countSms)
        val totalSum = findViewById<TextView>(R.id.totalSum)


        textFrom.text = from
        textTo.text = to
        bankName.text = if (bankID == 0) "PRIOR BANK" else "TECHNO BANK"
        countSms.text = " SMS COUNT:  $count"
        totalSum.text = " TOTAL SUM:  $sum  "


        val buttonBack = findViewById<Button>(R.id.button_back)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}