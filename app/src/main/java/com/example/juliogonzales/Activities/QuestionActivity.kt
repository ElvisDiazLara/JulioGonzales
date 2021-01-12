package com.example.juliogonzales.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.juliogonzales.Auxiliaries.getInstuction
import com.example.juliogonzales.Auxiliaries.getInstuctionDescription
import com.example.juliogonzales.Auxiliaries.shortToast
import com.example.juliogonzales.Brain
import com.example.juliogonzales.Models.ChecklistModel
import com.example.juliogonzales.R
import com.example.juliogonzales.Utils.Media
import com.example.juliogonzales.Utils.Utils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.popup_image_succes.view.*
import kotlinx.android.synthetic.main.popup_report_send.view.*
import java.io.IOException
import java.util.*

class QuestionActivity : AppCompatActivity() {

    // init Firebase
    private lateinit var database: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    //Variables Good
    internal var storage: FirebaseStorage ?=null
    internal var storageReference: StorageReference ?= null
    private  var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 1234

    // Photo
    var namePhoto: String = ""
    private var utils : Utils? = null
    private var media : Media? = null
    private var context : Context? = null
    var imageRui: Uri? = null
    private val ImageCaptureCode = 1001

    //Question Information
    var mail: String = ""
    var reportId: String = ""
    var instruction: String = ""
    var problemDescription: String = ""
    var instructionDescription: String = ""
    var option_selected = 0
    var actual_phase = 0

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        utils = Utils(this@QuestionActivity, this@QuestionActivity)
        context = this@QuestionActivity
        media = Media(this@QuestionActivity, this@QuestionActivity)

        //init Firebase
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        btnUpload.setOnClickListener {
            uploadFile()
        }
        permission()


        // Initial data
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upleads")




        actual_phase = Brain.instance.actual_phase
        add_evidence_btn.isEnabled = false

        // Set texts
        phase_num_txt.text = "${actual_phase+1}/${Brain.instance.max_phases+1}"
        instruction_txt.text = getInstuction(actual_phase, this)
        instruction_description.text = getInstuctionDescription(actual_phase, this)

        instruction = getInstuction(actual_phase, this)
        instructionDescription = getInstuctionDescription(actual_phase, this)

    }

    private fun uploadFile() {
        if (imageRui != null){
            database = FirebaseDatabase.getInstance().getReference("Reportes")
            val phase = actual_phase+1
            val QuestionNumber = "question_$phase"
            val counter = 0
            val photoNumber = "photo_$counter"
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()


            val imageRef = storageReference!!.child("images/$reportId/$QuestionNumber/"+UUID.randomUUID().toString())
            imageRef.putFile(imageRui!!)
                .addOnSuccessListener {
                    btnUpload.visibility = View.GONE
                    layoutOtherPhoto.visibility = View.GONE
                    counter + 1
                    val str = it.metadata?.path.toString()
                    val delimiter1 = "/"

                    val parts = str.split(delimiter1)

                    Log.d("DEBUGSPLIT", parts[2])
                    namePhoto = parts[2]
                    Log.d("DEBUGSPLIT",it.metadata?.path)
                    progressDialog.dismiss()
                    val dialogBuilder = AlertDialog.Builder(this@QuestionActivity)
                    dialogBuilder.setTitle("Success")

                    val view = this.layoutInflater.inflate(R.layout.popup_image_succes,null)
                    dialogBuilder.setView(view)
                    val alertDialog = dialogBuilder.create()
                    alertDialog.show()

                    view.btnImageSucces.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    add_evidence_btn.visibility = View.VISIBLE
                    add_evidence_btn.isEnabled = true
                    add_evidence_btn.setBackgroundResource(R.drawable.btn_round_blue)
                    add_evidence_btn.text = "Next"

                    Toast.makeText(this,"File Uploaded",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
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

    private fun permission(){
        utils?.checkAndRequestPermissions()
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

    // ======================== FUNCTIONS ==========================================

    private fun saveCheckList(){

        val bundle = intent.extras
        reportId = bundle?.get("form") as String
        mail = bundle.get("mail") as String
        database = FirebaseDatabase.getInstance().getReference("Reportes")
        val phase = actual_phase+1
        val optionSelected = option_selected.toString()
        var newoptionSelected = ""
        btnAddOtherPhoto.setOnClickListener {
            openCamera()
        }
        if (optionSelected == "1"){
            newoptionSelected = "Passed"
        }else if(optionSelected == "2"){
            newoptionSelected = "Not Passed"
        }
        Log.d("DebugDescription", "$instructionDescription $instruction")
        problemDescription = editTextProblemDescribe.text.toString()
        val QuestionNumber = "question_$phase"
        val checklist = ChecklistModel(newoptionSelected, instruction, instructionDescription,problemDescription)

        database.child(reportId).child("checklist").child(QuestionNumber).setValue(checklist).addOnCompleteListener{
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
        }

    }

    override fun onStart() {
        //=========== CLICK LISTENERS ================
        addEvidence()
        ok_btn.setOnClickListener {
            add_evidence_btn.visibility = View.VISIBLE
            add_evidence_btn.isEnabled = true
            ok_btn.visibility = View.GONE
            btnOkBlue.visibility = View.VISIBLE
            option_selected = 1
            add_evidence_btn.setBackgroundResource(R.drawable.btn_round_blue)
            report_issue_layout.visibility = View.GONE
            add_evidence_btn.text = "Next"
            saveCheckList()
        }

        report_issue_btn.setOnClickListener {
//            add_evidence_btn.visibility = View.VISIBLE
//            btnPhoto.visibility = View.VISIBLE
            btnPhoto.isEnabled = true
            report_issue_btn.visibility = View.GONE
            btnErrorRed.visibility = View.VISIBLE
            option_selected = 2
            txtProblemDescribe.visibility = View.VISIBLE
            editTextProblemDescribe.visibility = View.VISIBLE
            btnSendDescription.visibility = View.VISIBLE
            instruction_ok_layout.visibility = View.GONE
//            saveCheckList()
        }

        btnSendDescription.setOnClickListener {
            saveCheckList()
        }

        super.onStart()
    }

    fun addEvidence(){
        btnPhoto.setOnClickListener {
            setupCamera()
        }
        add_evidence_btn.setOnClickListener{
            if (option_selected == 0) {
//                dispatchTakePictureIntent()
            } else {
                actual_phase++
                // check if there are more phases
                if (actual_phase > Brain.instance.max_phases) {
                    shortToast("Max phases reached!", this)
                    return@setOnClickListener
                }
                Brain.instance.actual_phase = actual_phase
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("mail",mail)
                intent.putExtra("form",reportId)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onResume() {
        if(actual_phase == 18){
            add_evidence_btn.setOnClickListener {
                option_selected = 0
                Brain.instance.actual_phase = 0
                actual_phase = 0
                val intent = Intent(this,ReportSummaryActivity::class.java)
                intent.putExtra("mail",mail)
                intent.putExtra("form",reportId)
                startActivity(intent)
                finish()
            }
        }
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null){
            imageRui = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageRui)
                layoutOtherPhoto.visibility = View.VISIBLE
                txtProblemDescribe.visibility = View.GONE
                editTextProblemDescribe.visibility = View.GONE
                imgEvidence.visibility = View.VISIBLE
                layoutGrade.visibility = View.GONE
                imgEvidence!!.setImageBitmap(bitmap)
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
//        If the camera captured its succesful
        if (resultCode == Activity.RESULT_OK){
            btnUpload.visibility = View.VISIBLE
            txtProblemDescribe.visibility = View.GONE
            editTextProblemDescribe.visibility = View.GONE
            layoutOtherPhoto.visibility = View.VISIBLE
            imgEvidence.visibility = View.VISIBLE
            btnPhoto.visibility = View.GONE
            layoutGrade.visibility = View.GONE
            imgEvidence.setImageURI(imageRui)
        }
    }


}
