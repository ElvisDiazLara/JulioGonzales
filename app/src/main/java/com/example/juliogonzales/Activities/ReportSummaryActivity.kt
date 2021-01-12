package com.example.juliogonzales.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.juliogonzales.Models.ChecklistModel
import com.example.juliogonzales.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_last_report_summary.*
import kotlinx.android.synthetic.main.activity_report_summary.*

class ReportSummaryActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var Question1Reference: DatabaseReference
    var reportId: String = ""
    var mail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_summary)

        // Get the id of the Report
        val bundle = intent.extras
        reportId = bundle?.get("form") as String
        mail = bundle.get("mail") as String






        super.onStart()
    }

    override fun onResume() {
        getInformation()
        super.onResume()
    }
    override fun onStart() {
//        getInformation()

        btnNextSummary.setOnClickListener {
            val intent = Intent(this, SendReportActivity::class.java)
            intent.putExtra("mail",mail)
            intent.putExtra("form",reportId)
            startActivity(intent)
            finish()
        }
        super.onStart()
    }

    fun getInformation(){
        question1()
        question2()
        question3()
        question4()
        question5()
        question6()
        question7()
        question8()
        question9()
        question10()
        question11()
        question12()
        question13()
        question14()
        question15()
        question16()
        question17()
        question18()
        question19()
    }

    fun question1(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_1")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI ---
                // Obtenga el objeto Post y use los valores para actualizar la interfaz de usuario
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]

                post?.let {
                    txtQuestion1.text = it.optionSelect
                    if(it.optionSelect == "Passed"){
                        imgPass1.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass1.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_1")
            intent.putExtra("editar",true)
            startActivity(intent)
        }

        btnEditQuestion1.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_1")
            startActivity(intent)
        }
    }
    fun question2(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_2")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    txtQuestion2.text = it.optionSelect
                    if(it.optionSelect == "Passed"){
                        imgPass2.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass2.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit2.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_2")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion2.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_2")
            startActivity(intent)
        }
    }
    fun question3(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_3")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    txtQuestion3.text = it.optionSelect
                    if(it.optionSelect == "Passed"){
                        imgPass3.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass3.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit3.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_3")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion3.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_3")
            startActivity(intent)
        }
    }
    fun question4(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_4")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass4.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass4.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion4.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit4.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_4")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion4.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_4")
            startActivity(intent)
        }
    }
    fun question5(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_5")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass5.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass5.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion5.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit5.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_5")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion5.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_5")
            startActivity(intent)
        }
    }
    fun question6(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_6")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass6.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass6.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion6.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit6.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_6")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion6.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_6")
            startActivity(intent)
        }
    }
    fun question7(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_7")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass7.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass7.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion7.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit7.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_7")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion7.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_7")
            startActivity(intent)
        }
    }
    fun question8(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_8")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass8.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass8.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion8.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit8.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_8")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion8.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_8")
            startActivity(intent)
        }
    }
    fun question9(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_9")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass9.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass9.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion9.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit9.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_9")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion9.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_9")
            startActivity(intent)
        }
    }
    fun question10(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_10")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass10.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass10.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion10.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit10.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_10")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion10.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_10")
            startActivity(intent)
        }
    }
    fun question11(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_11")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass11.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass11.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion11.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit11.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_11")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion11.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_11")
            startActivity(intent)
        }
    }
    fun question12(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_12")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass12.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass12.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion12.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit12.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_12")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion12.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_12")
            startActivity(intent)
        }
    }
    fun question13(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_13")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass13.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass13.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion13.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit13.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_13")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion13.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_13")
            startActivity(intent)
        }
    }
    fun question14(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_14")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass14.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass14.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion14.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit14.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_14")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion14.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_14")
            startActivity(intent)
        }
    }
    fun question15(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_15")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass15.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass15.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion15.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit15.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_15")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion15.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_15")
            startActivity(intent)
        }
    }
    fun question16(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_16")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass16.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass16.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion16.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit16.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_16")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion16.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_16")
            startActivity(intent)
        }
    }
    fun question17(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_17")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass17.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass17.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion17.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit17.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_17")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion17.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_17")
            startActivity(intent)
        }
    }

    fun question18(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_18")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass18.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass18.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion18.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit18.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_18")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion18.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_18")
            startActivity(intent)
        }
    }

    fun question19(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question_19")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    if(it.optionSelect == "Passed"){
                        imgPass19.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                    }else{
                        imgPass19.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion19.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidenceEdit19.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_19")
            intent.putExtra("editar",true)
            startActivity(intent)
        }
        btnEditQuestion19.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",reportId)
            intent.putExtra("Question","question_19")
            startActivity(intent)
        }
    }
}
