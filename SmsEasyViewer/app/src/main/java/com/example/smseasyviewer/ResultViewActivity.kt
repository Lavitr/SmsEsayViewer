package com.example.smseasyviewer

import SmsData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.happybirthday.adapter.ItemAdapter

class ResultViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_view)

        val myDataset = intent.getSerializableExtra("hashMap") as HashMap<String, Float>
        val listData = myDataset.toList().sortedByDescending { (_, value) -> value }
            .map { SmsData(it.first, it.second) }
        Log.d("list", myDataset.toString())
//        Log.d("list", list.toString())

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, listData)
        recyclerView.setHasFixedSize(true)
        /////////////////////////

        val buttonBack = findViewById<Button>(R.id.button_back)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}