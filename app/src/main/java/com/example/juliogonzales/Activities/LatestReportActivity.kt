package com.example.juliogonzales.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.Adapter.LatestReportAdapter
import com.example.juliogonzales.Models.LastReportModel
import com.example.juliogonzales.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_latest_report.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class LatestReportActivity : AppCompatActivity() {

    private lateinit var databaseLatest: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_report)

        val passed = 0
        for (i in 1..17) {
            Log.d("DebugFor", i.toString())
        }

        databaseLatest = FirebaseDatabase.getInstance().reference.child("Reportes")
        databaseLatest.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {

                val orders = ArrayList<LastReportModel>()
                for(child in data.children.iterator()){
                    val order = child.getValue(LastReportModel::class.java)
                    if(order != null){
                        order.id = child.key
                        order.date = child.child("form").child("date").value.toString()
                        orders.add(order)
                        Log.d("DEBUGLIST", "${order.id}--${order.date}")
                    }else{
                        toast("No hay órdenes por el momento")
                    }
                }
                val adapter = LatestReportAdapter(this@LatestReportActivity,orders)
                recyclerViewLatest.layoutManager = LinearLayoutManager(this@LatestReportActivity)
                recyclerViewLatest.adapter = adapter
            }
        })
    }
}
