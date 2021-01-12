package com.example.juliogonzales.Activities

import ImageAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_final_images_report.*
import kotlinx.android.synthetic.main.activity_images_report.list_recycler_view
import kotlinx.android.synthetic.main.activity_images_report.txtBack
import kotlinx.android.synthetic.main.activity_images_report.txtview1
import kotlinx.android.synthetic.main.activity_images_report.txtviewEdit

class FinalImagesReportActivity : AppCompatActivity(), imageCallback {

    internal var storageReference: StorageReference?= null
    private val images: MutableList<String> = ArrayList()
    var summary: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_images_report)

        putImageView()
    }


    override fun onRestart() {
        putImageView()
        super.onRestart()
    }


    fun putImageView(){

        val booleanEditable: Boolean = false
        // Get the id of the Report
        val bundle = intent.extras
        val id = bundle?.get("reportID") as String
        val editar = bundle.getBoolean("editar")

        if(editar){
            txtview1.visibility = View.GONE
            txtviewEdit.visibility = View.VISIBLE
            btnAddPhoto.visibility = View.VISIBLE
        }else if(!editar){
            txtview1.visibility = View.VISIBLE
            txtviewEdit.visibility = View.INVISIBLE
            btnAddPhoto.visibility = View.INVISIBLE
            btnAddPhoto.visibility = View.GONE
        }

        txtBack.setOnClickListener {
            onBackPressed()
        }

        btnAddPhoto?.setOnClickListener {
            val intent = Intent(this, AddImagesFinalActivity::class.java)
            intent.putExtra("reportID",id)
            intent.putExtra("Question","final")
            intent.putExtra("editar",true)
            startActivity(intent)
        }

        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child("images/$id/extras")
        // Create a reference to a file from a Google Cloud Storage URI

        val storageReference = FirebaseStorage.getInstance().reference


        Log.d("Bueno", listRef.toString())
        Log.d("DEBUGID",id)
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                if(listResult.items.isEmpty()){
                    imgEmpty.visibility = View.VISIBLE
                }
                listResult.prefixes.forEach { prefix ->
                    Log.d("Buenoprefix", prefix.toString())
                }

                listResult.items.forEach { item ->

                    images.add(item.toString())
                    Log.d("Bueno", images.toString())
                    Log.d("Bueno", item.toString())
                }

                Log.d("Bueno", images.toString())
                val adapter = ImageAdapter(
                    this@FinalImagesReportActivity,
                    images,storageReference,
                    id,
                    booleanEditable,
                    this@FinalImagesReportActivity)
                list_recycler_view.layoutManager = LinearLayoutManager(this@FinalImagesReportActivity, RecyclerView.HORIZONTAL, false)
                LinearLayoutManager.HORIZONTAL
                list_recycler_view.adapter = adapter
            }
            .addOnFailureListener {
                Log.d("Bueno", "Error")
            }
    }
    override fun deleteSucces(){
        onBackPressed()
    }

}

