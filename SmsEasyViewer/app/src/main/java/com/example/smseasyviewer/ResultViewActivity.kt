package com.example.smseasyviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.happybirthday.adapter.ItemAdapter
import com.example.happybirthday.model.SmsData
import com.example.smseasyviewer.data.Datasource

class ResultViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_view)

//        val myDataset = Datasource().loadAffirmations()
        val myDataset = intent.getSerializableExtra("hashMap") as HashMap<String, Int>
        val listData = myDataset.toList().map { SmsData(it.first, it.second) }
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