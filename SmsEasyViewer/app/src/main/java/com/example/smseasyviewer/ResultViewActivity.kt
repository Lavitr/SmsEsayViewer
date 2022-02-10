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
import com.google.android.material.textfield.TextInputLayout

class ResultViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_view)

        val myDataset = intent.getSerializableExtra("hashMap") as HashMap<String, Int>
        val count = intent.getIntExtra("count", 0)
        val from = intent.getStringExtra("from")
        val to = intent.getStringExtra("to")
        val listData = myDataset.toList().sortedByDescending { (_, value) -> value }
            .map { SmsData(it.first, it.second) }
//        val listData = Datasource().loadSmsData()
        Log.d("count!!!!", "$count")
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, listData)
        recyclerView.setHasFixedSize(true)
        /////////////////////////
        val textFrom = findViewById<TextView>(R.id.textViewFrom)
        val textTo = findViewById<TextView>(R.id.textViewTo)
        textFrom.text = from
        textTo.text = to
//        val countText = findViewById<TextView>(R.id.count)
//        countText.text = count.toString()

        val buttonBack = findViewById<Button>(R.id.button_back)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}