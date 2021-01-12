package com.example.juliogonzales.Activities

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
import kotlinx.android.synthetic.main.activity_add_images_final.*
import kotlinx.android.synthetic.main.activity_question.*
import java.io.IOException
import java.util.*

class AddImagesFinalActivity : AppCompatActivity() {

    // init Firebase
    private lateinit var database: DatabaseReference
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    //Variables Good
    internal var storage: FirebaseStorage ?=null
    internal var storageReference: StorageReference?= null
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
    var id:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_images_final)

        txtBackEditFinal.setOnClickListener {
            onBackPressed()
        }

        utils = Utils(this@AddImagesFinalActivity, this@AddImagesFinalActivity)
        context = this@AddImagesFinalActivity
        media = Media(this@AddImagesFinalActivity, this@AddImagesFinalActivity)

        // Get the id of the Report
        val bundle = intent.extras
        id = bundle?.get("reportID") as String

        //init Firebase
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        btnPhotoEditFinal.setOnClickListener {

            setupCamera()
        }

        btnUploadEditFinal.setOnClickListener {
            uploadFile()
        }
        //=========== CLICK LISTENERS ================
        addEvidence()

        // Initial data
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upleads")

    }
    // ======================== FUNCTIONS ==========================================

    private fun uploadFile() {
        if (imageRui != null){
            database = FirebaseDatabase.getInstance().getReference("Reportes")
            val counter = 0
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()


            val imageRef = storageReference!!.child("images/$id/extras/"+ UUID.randomUUID().toString())
            imageRef.putFile(imageRui!!)
                .addOnSuccessListener {
                    counter + 1
                    var str = it.metadata?.path.toString()
                    var delimiter1 = "/"

                    val parts = str.split(delimiter1)

                    Log.d("DEBUGSPLIT", parts[2])
                    namePhoto = parts[2]
                    Log.d("DEBUGSPLIT",it.metadata?.path)
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


    fun addEvidence(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null){
            imageRui = data.data
            try {

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageRui)
                layoutOtherPhoto.visibility = View.VISIBLE
                imgEvidenceEditFinal!!.setImageBitmap(bitmap)
               // utils?.showSnackbar()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }else{
            Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_LONG).show()
          //  utils?.showSnackbarFailed()
        }
//        If the camera captured its succesful
        if (resultCode == Activity.RESULT_OK){
            Utils(this, this).showSnackbar()
            btnUploadEditFinal.visibility = View.VISIBLE
            btnPhotoEditFinal.text = "Add another Photo"
            imgEvidenceEditFinal.setImageURI(imageRui)
            utils?.showSnackbar()
        }else{
            utils?.showSnackbarFailed()
        }
    }


}
