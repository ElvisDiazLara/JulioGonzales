package com.example.juliogonzales.Activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juliogonzales.Adapter.ListAdapter
import com.example.juliogonzales.Auxiliaries.shortToast
import com.example.juliogonzales.Models.Question
import com.example.juliogonzales.R
import com.example.juliogonzales.Utils.Utils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_all_questions.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.confirm_save_questions.view.*
import kotlinx.android.synthetic.main.popup_report_send.view.*
import java.util.*
import kotlin.collections.HashMap


class AllQuestionsActivity : AppCompatActivity() {

    // init Firebase
    private lateinit var database: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    var mail: String = ""
    var reportId: String = ""
    var position: Int = 0

    private var utils : Utils? = null


    private val allQuestions = listOf(
        Question("Passed","Check the bumpers", "Smuggling free?", "" ),
        Question("Passed","Check the engine", "Smuggling free?", "" ),
        Question("Passed","Check the tires", "Smuggling free?", "" ),
        Question("Passed","Check the floor inside the truck", "Smuggling free?", "" ),
        Question("Passed","Check the fuel tanks", "Smuggling free?", "" ),
        Question("Passed","Check the cab", "Smuggling free?", "" ),
        Question("Passed","Check the air tanks", "Smuggling free?", "" ),
        Question("Passed","Check the drive shafts", "Smuggling free?", "" ),
        Question("Passed","Check the fifth wheel", "Smuggling free?", "" ),
        Question("Passed","Check the outside/undercarriage", "Smuggling free?", "" ),
        Question("Passed","Check the outside/inside doors", "Smuggling free?", "" ),
        Question("Passed","Check the floot inside the trailer", "Smuggling free?", "" ),
        Question("Passed","Check the sidewalls", "Smuggling free?", "" ),
        Question("Passed","Check the frontwall", "Smuggling free?", "" ),
        Question("Passed","Check the rooof", "Smuggling free?", "" ),
        Question("Passed","Check the refrigeration unit", "Smuggling free?", "" ),
        Question("Passed","Check the exhaust", "Smuggling free?", "" ),
        Question("Passed","Are contamination from pests, stems and leaves of plants in the trailer or soil, seeds, animal, bleeds, visible or detected", "Smuggling free?", "" ),
        Question("Passed","Are container doors, handles, bars, bolts, rivets, bracktes, locking mechanisms unaltered?", "Smuggling free?", "" )

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_questions)
        utils = Utils(this, this)

        onClicks()

        permission()

        rvAllQuestions.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(this@AllQuestionsActivity)
            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(allQuestions)
        }

    }

    private fun onClicks(){
        btSaveAll.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.confirm_save_questions)

        val btSaeToFirebase = dialog.findViewById(R.id.btSaveToFirebase) as Button
        val btCancel = dialog.findViewById(R.id.btCancel) as Button

        btSaeToFirebase.setOnClickListener {
            dialog.dismiss()
            saveToFirebase()
        }
        btCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun permission(){
        utils?.checkAndRequestPermissions()
    }

    private fun saveToFirebase(){
        rvAllQuestions.visibility = View.INVISIBLE
        lySaveData.visibility = View.VISIBLE
        val bundle = intent.extras
        reportId = bundle?.get("form") as String
        mail = bundle.get("mail") as String
        database = FirebaseDatabase.getInstance().getReference("Reportes")


        Log.d("DATA:","$reportId //// $mail" )


        val questions: HashMap<String, Question> = HashMap()
        questions.put("question_1", allQuestions[0])
        questions.put("question_2", allQuestions[1])
        questions.put("question_3", allQuestions[2])
        questions.put("question_4", allQuestions[3])
        questions.put("question_5", allQuestions[4])
        questions.put("question_6", allQuestions[5])
        questions.put("question_7", allQuestions[6])
        questions.put("question_8", allQuestions[7])
        questions.put("question_9", allQuestions[8])
        questions.put("question_10", allQuestions[9])
        questions.put("question_11", allQuestions[10])
        questions.put("question_12", allQuestions[11])
        questions.put("question_13", allQuestions[12])
        questions.put("question_14", allQuestions[13])
        questions.put("question_15", allQuestions[14])
        questions.put("question_16", allQuestions[15])
        questions.put("question_17", allQuestions[16])
        questions.put("question_18", allQuestions[17])
        questions.put("question_19", allQuestions[18])





        for (i in 0..(allQuestions.size - 1)) {
             position = i +1

            Log.d("Numero:", "question_$position //// $i")
            database.child(reportId).child("checklist").child("question_$position").setValue(allQuestions[i])
          /*     .addOnCompleteListener{
             //   if(optionSelected == "2"){
                   // btnPhoto.visibility = View.VISIBLE
                  //  editTextProblemDescribe.isEnabled = false
                   // btnSendDescription.visibility = View.GONE
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Success")

                    val view = this.layoutInflater.inflate(R.layout.popup_report_send,null)
                    dialogBuilder.setView(view)
                    val alertDialog = dialogBuilder.create()
                    alertDialog.show()

                    view.btnokDescription.setOnClickListener {
                        alertDialog.dismiss()
                    }
                //} //end if
            }*/
        }
//change value 19 when questions increase or decrease 17 = default
        if (position == 19){

            rvAllQuestions.visibility = View.VISIBLE
            lySaveData.visibility = View.GONE

            val dialogBuilder = AlertDialog.Builder(this)


            val view = this.layoutInflater.inflate(R.layout.confirm_save_questions,null)
            dialogBuilder.setView(view)
            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            view.tvDesc.visibility = View.GONE
            view.btCancel.visibility = View.GONE
            view.tvSure.text = "Questions saved successfully!"
            view.btSaveToFirebase.text = "OK"

            view.btSaveToFirebase.setOnClickListener {
                alertDialog.dismiss()
                val intent = Intent(this,ReportSummaryActivity::class.java)
                intent.putExtra("mail",mail)
                intent.putExtra("form",reportId)
                startActivity(intent)
                finish()
            }
        }

      /*  database.child(reportId).child("checklist").child(QuestionNumber).setValue(checklist).addOnCompleteListener{
            if(optionSelected == "2"){
                btnPhoto.visibility = View.VISIBLE
                editTextProblemDescribe.isEnabled = false
                btnSendDescription.visibility = View.GONE
                val dialogBuilder = AlertDialog.Builder(this@QuestionActivity)
                dialogBuilder.setTitle("Success")

                val view = this.layoutInflater.inflate(R.layout.popup_report_send,null)
                dialogBuilder.setView(view)
                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                view.btnokDescription.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }*/


    }
}
