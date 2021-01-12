package com.example.juliogonzales.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.Models.ChecklistModel
import com.example.juliogonzales.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_images_report.*
import kotlinx.android.synthetic.main.activity_last_report_summary.*
import kotlinx.android.synthetic.main.activity_report_summary.*

class LastReportSummaryActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var storage = FirebaseStorage.getInstance()
    private lateinit var Question1Reference: DatabaseReference
    var passedReport: Int = 0
    var reportId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_report_summary)

        // Get the id of the Report
        val bundle = intent.extras
        reportId = bundle?.get("reportID") as String

        var passCount = 0

        //default 17 change to 19
        for (i in 1..19) {
//            Log.d("DebugCount", "question_$i $passCount")
            passCount += 1
            val myTopPostsQuery = FirebaseDatabase.getInstance().reference
                .child("Reportes").child("checklist").child("question_$i").child("optionSelect")
//            val passed = myTopPostsQuery.child("optionSelect")
            // My top posts by number of stars
            myTopPostsQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {

                        passCount +=1
                        val postListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // Get Post object and use the values to update the UI
                                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                                post?.let {
                                    if(it.optionSelect == "Passed"){
                                        passCount += 1
//                                        Log.d("DebugCount", passCount.toString())
                                    }else{
                                        passCount -= 1
//                                        Log.d("DebugCount", passCount.toString())
                                    }
                                }

                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                            }
                        }
                        myTopPostsQuery.addValueEventListener(postListener)


                        Log.d("DebugFor", dataSnapshot.toString())
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }

//        Log.d("DebugCount", passCount.toString())

        super.onStart()
        Log.d("DebugCount", passedReport.toString())

    }

    override fun onResume() {
        getInformation()
        Log.d("DebugCount", passedReport.toString())
        super.onResume()
    }
    override fun onStart() {
        Log.d("DebugCount", passedReport.toString())
//        getInformation()

        btnNextSummaryLastReport.setOnClickListener {
            val intent = Intent(this, SummaryLastReport::class.java)
            intent.putExtra("reportID",reportId)
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
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]

                post?.let {
                    txtQuestion1LastReport.text = it.optionSelect
                    if(it.optionSelect == "Passed"){
                        passedReport += 1
                        imgPass1LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence.visibility = View.GONE
                    }else{
                        passedReport -= 0
                        imgPass1LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    Log.d("DEBUGPASS", it.optionSelect)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_1")
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
                    txtQuestion2LastReport.text = it.optionSelect
                    if(it.optionSelect == "Passed"){
                        imgPass2LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence2.visibility = View.GONE
                    }else{
                        imgPass2LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence2.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_2")
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
                    txtQuestion3LastReport.text = it.optionSelect
                    if(it.optionSelect == "Passed"){
                        imgPass3LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence3.visibility = View.GONE
                    }else{
                        imgPass3LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence3.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_3")
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
                        imgPass4LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence4.visibility = View.GONE
                    }else{
                        imgPass4LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion4LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence4.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_4")
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
                        imgPass5LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence5.visibility = View.GONE
                    }else{
                        imgPass5LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion5LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence5.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_5")
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
                        imgPass6LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence6.visibility = View.GONE
                    }else{
                        imgPass6LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion6LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence6.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_6")
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
                        imgPass7LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence7.visibility = View.GONE
                    }else{
                        imgPass7LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion7LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence7.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_7")
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
                        imgPass8LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence8.visibility = View.GONE
                    }else{
                        imgPass8LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion8LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence8.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_8")
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
                        imgPass9LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence9.visibility = View.GONE
                    }else{
                        imgPass9LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion9LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence9.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_9")
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
                        imgPass10LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence10.visibility = View.GONE
                    }else{
                        imgPass10LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion10LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence10.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_10")
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
                        imgPass11LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence11.visibility = View.GONE
                    }else{
                        imgPass11LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion11LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence11.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_11")
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
                        imgPass12LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence12.visibility = View.GONE
                    }else{
                        imgPass12LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion12LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence12.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_12")
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
                        imgPass13LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence13.visibility = View.GONE
                    }else{
                        imgPass13LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion13LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence13.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_13")
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
                        imgPass14LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence14.visibility = View.GONE
                    }else{
                        imgPass14LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion14LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence14.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_14")
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
                        imgPass15LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence15.visibility = View.GONE
                    }else{
                        imgPass15LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion15LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence15.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_15")
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
                        imgPass16LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence16.visibility = View.GONE
                    }else{
                        imgPass16LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion16LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence16.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_16")
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
                        imgPass17LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence17.visibility = View.GONE
                    }else{
                        imgPass17LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion17LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence17.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_17")
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
                        imgPass18LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence18.visibility = View.GONE
                    }else{
                        imgPass18LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion18LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence18.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_18")
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
                        imgPass19LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_summary_ok))
                        btnEvidence19.visibility = View.GONE
                    }else{
                        imgPass19LastReport.setImageDrawable(resources.getDrawable(R.drawable.ic_report_error))
                    }
                    txtQuestion19LastReport.text = it.optionSelect
                    Log.d("DEBUGPASS", it.optionSelect)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
        btnEvidence19.setOnClickListener {
            val intent = Intent(this, ImagesReportActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("question","question_19")
            startActivity(intent)
        }

    }
}
