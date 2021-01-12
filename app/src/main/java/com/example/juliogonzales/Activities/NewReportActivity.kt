package com.example.juliogonzales.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.juliogonzales.Models.ReportModel
import com.example.juliogonzales.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_new_report.*
import kotlinx.android.synthetic.main.confirm_save_questions.view.*
import kotlinx.android.synthetic.main.option_question.view.*

class NewReportActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var txtProperty : EditText
    lateinit var txtVehicleNumber : EditText
    lateinit var txtLicence : EditText
    lateinit var txtSecurityPerson : EditText
    lateinit var txtEmailReport : EditText
    private lateinit var createNewReport : Button


    private var email: String = ""
    private var reportId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_report)

        txtProperty = findViewById(R.id.txtProperty)
        txtVehicleNumber = findViewById(R.id.txtVehicleNumber)
        txtLicence = findViewById(R.id.txtLicence)
        txtSecurityPerson = findViewById(R.id.txtSecurityPerson)
        txtEmailReport = findViewById(R.id.txtEmailReport)
        createNewReport = findViewById(R.id.createNewReport)

        saveReport()
    }


    private fun saveReport() {

        createNewReport.setOnClickListener {
            progressNewReport.visibility = View.VISIBLE
            val propertyName = txtProperty.text.toString().trim()
            val vehicleNumber = txtVehicleNumber.text.toString().trim()
            val licence = txtLicence.text.toString().trim()
            val securityPerson = txtSecurityPerson.text.toString().trim()
           // val email = txtEmailReport.text.toString().trim()
            email = txtEmailReport.text.toString().trim()

            if(propertyName.isEmpty()){
                progressNewReport.visibility = View.GONE
                txtProperty.error = "Por favor ingresa un Nombre"
                return@setOnClickListener
            }
            if(vehicleNumber.isEmpty()){
                progressNewReport.visibility = View.GONE
                txtVehicleNumber.error = "Por favor ingresa un Nombre"
                return@setOnClickListener
            }
            if(licence.isEmpty()){
                progressNewReport.visibility = View.GONE
                txtLicence.error = "Por favor ingresa un Nombre"
                return@setOnClickListener
            }
            if(securityPerson.isEmpty()){
                progressNewReport.visibility = View.GONE
                txtSecurityPerson.error = "Por favor ingresa un Nombre"
                return@setOnClickListener
            }
            if(email.isEmpty()){
                progressNewReport.visibility = View.GONE
                txtEmailReport.error = "Por favor ingresa un Nombre"
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("Reportes")
            //val reportId = database.push().key
            reportId = database.push().key
            val report = ReportModel(propertyName,reportId, vehicleNumber, licence, securityPerson, email)

            database.child(reportId.toString()).child("form").setValue(report).addOnCompleteListener{
                progressNewReport.visibility = View.GONE
                showOptionQuestions()
               // val intent = Intent(this, QuestionActivity::class.java)
              /*  val intent = Intent(this, AllQuestionsActivity::class.java)
                intent.putExtra("mail",email)
                intent.putExtra("form",reportId)
                println(reportId)
                Log.d("NEW REPORT ACTIVITY:","$reportId //// $email" )
                startActivity(intent)
                finish()
                Toast.makeText(this,"Reporte Creado Correctamente", Toast.LENGTH_SHORT).show()*/
            }
        }
    }

    private fun showOptionQuestions(){
        val dialogBuilder = AlertDialog.Builder(this)


        val view = this.layoutInflater.inflate(R.layout.option_question,null)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        view.btGotoAllQuestions.setOnClickListener {
            alertDialog.dismiss()
            val intent = Intent(this,AllQuestionsActivity::class.java)
            intent.putExtra("mail",email)
            intent.putExtra("form",reportId)
            startActivity(intent)
            finish()
        }

        view.btGotoQuestion.setOnClickListener {
            alertDialog.dismiss()
            val intent = Intent(this,QuestionActivity::class.java)
            intent.putExtra("mail",email)
            intent.putExtra("form",reportId)
            startActivity(intent)
            finish()
        }
    }
}
