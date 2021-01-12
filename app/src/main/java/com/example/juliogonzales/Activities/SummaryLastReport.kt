package com.example.juliogonzales.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.juliogonzales.Models.ReportModel
import com.example.juliogonzales.Models.SummaryReportModel
import com.example.juliogonzales.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_report_summary.*
import kotlinx.android.synthetic.main.activity_send_report.*
import kotlinx.android.synthetic.main.activity_summary_last_report.*
import java.text.SimpleDateFormat
import java.util.*




class SummaryLastReport : AppCompatActivity() {

    private val context: Context? = null
    var reportId: String = ""
    private lateinit var reportReference: DatabaseReference
    private lateinit var Question1Reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary_last_report)

        // Get the id of the Report
        val bundle = intent.extras
        reportId = bundle?.get("reportID") as String

        createNewReportLastReport.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSeePhotosLastReport.setOnClickListener {
            val intent = Intent(this,FinalImagesReportActivity::class.java)
            intent.putExtra("editar",false)
            intent.putExtra("reportID", reportId)
            startActivity(intent)
            finish()
        }

//        txtDateDetail.setOnClickListener {
        openDatePicker()
//        }

//        saveReport()

    }

    override fun onStart() {
        loadInformation()
        super.onStart()
    }

    fun loadInformation(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("form")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(SummaryReportModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    txtPropertyOfLastReport.text = it.propertyName
                    txtVehicleNumberSendReportLastReport.text = it.vehicleNumber
                    txtLicenceSendReportLastReport.text = it.licence
                    txtSecurityPersonSendReportLastReport.text = it.securityPerson
                    txtEmailReportSendReportLastReport.text = it.email
                    txtDateDetailLastReport.text = it.date
                    txtSecurityPersonSummary.text = it.securityPerson
                    txtSealNumbersSummary.text = it.sealNumber
                    txtPersonAfixedSummary.text = it.personAfixed
                    txtPersonVerifiedSummary.text = it.personVerified


                    Log.d("DEBUGTXT",it.propertyName )
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
    }

    private fun openDatePicker() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        txtDateDetailLastReport.text = currentDate

    }
}
