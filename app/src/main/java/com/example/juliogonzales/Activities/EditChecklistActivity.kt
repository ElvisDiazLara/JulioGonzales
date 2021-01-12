package com.example.juliogonzales.Activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.juliogonzales.Models.ChecklistModel
import com.example.juliogonzales.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_checklist.*
import kotlinx.android.synthetic.main.activity_edit_checklist.add_evidence_btn
import kotlinx.android.synthetic.main.activity_edit_checklist.btnErrorRed
import kotlinx.android.synthetic.main.activity_edit_checklist.btnOkBlue
import kotlinx.android.synthetic.main.activity_edit_checklist.instruction_ok_layout
import kotlinx.android.synthetic.main.activity_edit_checklist.ok_btn
import kotlinx.android.synthetic.main.activity_edit_checklist.report_issue_btn
import kotlinx.android.synthetic.main.activity_edit_checklist.report_issue_layout
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.popup_report_send.view.*
import java.io.IOException
import java.util.*

class EditChecklistActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    internal var storage: FirebaseStorage?=null
    internal var storageReference: StorageReference?= null
    private lateinit var saveQuestionReference: DatabaseReference
    var reportId: String = ""
    var question: String = ""
    var instruction: String = ""
    var problemDescriptionEdit: String = ""
    var imageRui: Uri? = null
    private val ImageCaptureCode = 1001
    private val PICK_IMAGE_REQUEST = 1234
    var instructionDescription: String = ""
    var optionSelected = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_checklist)

        // Get the id of the Report
        val bundle = intent.extras
        reportId = bundle?.get("reportID") as String
        question = bundle.get("Question") as String
        //init Firebase
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

//        btnPhotoEdit?.setOnClickListener {
//            setupCamera()
//        }
        btnUploadEdit?.setOnClickListener {
            uploadFile()
        }

        loadInformation()
    }

    override fun onStart() {
        listeners()
        super.onStart()
    }

    fun listeners(){
        ok_btn.setOnClickListener {
            add_evidence_btn.visibility = View.VISIBLE
            add_evidence_btn.isEnabled = true
            ok_btn.visibility = View.GONE
            btnOkBlue.visibility = View.VISIBLE
            optionSelected = 1
            add_evidence_btn.setBackgroundResource(R.drawable.btn_round_blue)
            report_issue_layout.visibility = View.GONE
            add_evidence_btn.text = "Next"
            saveData()
        }

        report_issue_btn.setOnClickListener {
//            add_evidence_btn.isEnabled = true
//            btnPhotoEdit.visibility = View.VISIBLE
            btnPhotoEdit.isEnabled = true
            report_issue_btn.visibility = View.GONE
            btnErrorRed.visibility = View.VISIBLE
            optionSelected = 2
            txtProblemDescribeEdit.visibility = View.VISIBLE
            editTextProblemDescribeEdit.visibility = View.VISIBLE
            btnSendDescriptionEdit.visibility = View.VISIBLE
            instruction_ok_layout.visibility = View.GONE
//            saveData()
        }
        btnSendDescriptionEdit.setOnClickListener {
            saveData()
        }

        add_evidence_btn.setOnClickListener {
            onBackPressed()
        }

        btnPhotoEditBlue.setOnClickListener {
            setupCamera()
        }
    }

    private fun uploadFile() {
        if (imageRui != null){
            database = FirebaseDatabase.getInstance().getReference("Reportes")
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()


            val imageRef = storageReference!!.child("images/$reportId/$question/"+ UUID.randomUUID().toString())
            imageRef.putFile(imageRui!!)
                .addOnSuccessListener {
                    btnUploadEdit.visibility = View.GONE
                    layoutOtherPhotoEdit.visibility = View.GONE
                    add_evidence_btn.visibility = View.VISIBLE
                    add_evidence_btn.isEnabled = true
                    add_evidence_btn.setBackgroundResource(R.drawable.btn_round_blue)
                    add_evidence_btn.text = "Next"
                    progressDialog.dismiss()
                    Toast.makeText(this,"File Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded "+progress.toInt() + "%...")
                }
        }
    }



    private fun openCamera(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera")
        imageRui = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        //Camera Intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageRui)
        startActivityForResult(cameraIntent,ImageCaptureCode)
    }

    private fun setupCamera(){
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.ic_search)
        builder.setTitle("Add an evidence")
        builder.setMessage("Add a file in the report. Tap to take a picture.")
        builder.setNeutralButton("Ok"){_,_ ->
            openCamera()
        }
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    fun saveData(){
        saveQuestionReference = FirebaseDatabase.getInstance().getReference("Reportes")
            .child(reportId).child("checklist").child(question)

        val optionSelected = optionSelected.toString()
        var newoptionSelected = ""
        if (optionSelected == "1"){
            newoptionSelected = "Passed"
        }else if(optionSelected == "2"){
            newoptionSelected = "Not Passed"
        }
        problemDescriptionEdit = editTextProblemDescribeEdit.text.toString()
        if(problemDescriptionEdit.isEmpty()){
            Toast.makeText(this, "Please add a description", Toast.LENGTH_SHORT).show()
        }
        val checklist = ChecklistModel(newoptionSelected, instruction, instructionDescription, problemDescriptionEdit)

        saveQuestionReference.setValue(checklist).addOnCompleteListener{
            if(optionSelected == "2"){
                btnPhotoEditBlue.visibility = View.VISIBLE
                editTextProblemDescribeEdit.isEnabled = false
                btnSendDescriptionEdit.visibility = View.GONE
                val dialogBuilder = AlertDialog.Builder(this@EditChecklistActivity)
                dialogBuilder.setTitle("Success")

                val view = this.layoutInflater.inflate(R.layout.popup_report_send,null)
                dialogBuilder.setView(view)
                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                view.btnokDescription.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null){
            imageRui = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageRui)
                txtProblemDescribeEdit.visibility = View.GONE
                editTextProblemDescribeEdit.visibility = View.GONE
                layoutOtherPhotoEdit.visibility = View.VISIBLE
                imgEvidenceEdit.visibility = View.VISIBLE
                btnPhotoEditBlue.visibility = View.GONE
                layoutGradeEdit.visibility = View.GONE
                imgEvidenceEdit!!.setImageBitmap(bitmap)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
//        If the camera captured its succesful
        if (resultCode == Activity.RESULT_OK){
            btnUploadEdit.visibility = View.VISIBLE
            txtProblemDescribeEdit.visibility = View.GONE
            editTextProblemDescribeEdit.visibility = View.GONE
            btnPhotoEditBlue.visibility = View.GONE
            layoutOtherPhotoEdit.visibility = View.VISIBLE
            imgEvidenceEdit.visibility = View.VISIBLE
            btnPhotoEdit.visibility = View.GONE
            layoutGradeEdit.visibility = View.GONE
            imgEvidenceEdit.setImageURI(imageRui)
        }
    }

    fun loadInformation(){
        // Initialize Database
        database = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child(question)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                // [START_EXCLUDE]
                post?.let {
                    instruction_txtEdit.text = it.instruction
                    instruction_descriptionEdit.text = it.instructionDescription
                    instruction = it.instruction
                    instructionDescription = it.instructionDescription
                    Log.d("DEBUGTXT", it.optionSelect + it.instruction + it.instructionDescription )
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        database.addValueEventListener(postListener)

    }
}
