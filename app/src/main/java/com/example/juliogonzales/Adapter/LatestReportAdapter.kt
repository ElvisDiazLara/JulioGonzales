package com.example.juliogonzales.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.Activities.LastReportSummaryActivity
import com.example.juliogonzales.Models.LastReportModel
import com.example.juliogonzales.R
import java.util.ArrayList

class LatestReportAdapter(val context: Context,
                          private val pendientesList: ArrayList<LastReportModel>) :
    RecyclerView.Adapter<LatestReportAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_last_report,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount() = pendientesList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = pendientesList[position]
        val id = order.id.toString()
        holder.TextViewID.text = "Report No."+ order.id
        holder.TextViewDate.text = order.date
        holder.layout.setOnClickListener {
            val intent = Intent(holder.layout.context, LastReportSummaryActivity::class.java)
            intent.putExtra("reportID",id)
            context.startActivity(intent)
        }
    }
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val layout = itemView.findViewById<ConstraintLayout>(R.id.layoutLastReport)
        val TextViewID = itemView.findViewById<TextView>(R.id.txtFolio)
        val TextViewDate = itemView.findViewById<TextView>(R.id.txtDate)
    }
}