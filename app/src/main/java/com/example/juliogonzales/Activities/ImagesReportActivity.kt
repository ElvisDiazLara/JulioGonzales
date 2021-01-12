package com.example.juliogonzales.Activities

import ImageAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_images_report.*


class ImagesReportActivity : AppCompatActivity(),imageCallback{

    internal var storageReference: StorageReference?= null
    private val images: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_report)

        val booleanEditable:Boolean = true


        // Get the id of the Report
        val bundle = intent.extras
        val id = bundle?.get("reportID") as String
        val question = bundle?.get("question") as String
        val editar = bundle?.get("editar")
        Log.d("DebugBorrar",editar.toString())

        if(editar==true){
            txtview1.visibility = View.GONE
            txtviewEdit.visibility = View.VISIBLE
            txtView2.visibility = View.VISIBLE
        }else{
            imgEmptyEdit.visibility = View.GONE
            txtview1.visibility = View.VISIBLE
            txtviewEdit.visibility = View.INVISIBLE
            txtView2.visibility = View.INVISIBLE
        }

        txtBack.setOnClickListener {
            onBackPressed()
        }

        txtView2?.setOnClickListener {
            val intent = Intent(this, EditChecklistActivity::class.java)
            intent.putExtra("reportID",id)
            intent.putExtra("Question",question)
            intent.putExtra("editar",true)
            startActivity(intent)
        }

    }

    override fun onStart() {
        loadImage()
        super.onStart()
    }

    fun loadImage(){
        val booleanEditable:Boolean = true

        // Get the id of the Report
        val bundle = intent.extras
        val id = bundle?.get("reportID") as String
        val question = bundle?.get("question") as String
        val editar = bundle?.get("editar")
        Log.d("DebugBorrar",editar.toString())
        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child("images/$id/$question")
        // WORKING CODE!
        // Create a reference to a file from a Google Cloud Storage URI

        val storageReference = FirebaseStorage.getInstance().reference


        Log.d("Bueno", listRef.toString())
        Log.d("DEBUGID",id)
        listRef.listAll().addOnSuccessListener { listResult ->

            progressEditImage.visibility = View.GONE

            if (listResult.items.isEmpty()){
                imgEmptyEdit.visibility = View.VISIBLE
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
                this@ImagesReportActivity,
                images,
                storageReference,
                id,
                booleanEditable,
                this@ImagesReportActivity
            )
            list_recycler_view.layoutManager = LinearLayoutManager(this@ImagesReportActivity, RecyclerView.HORIZONTAL, false)
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

interface imageCallback{
    fun deleteSucces()
}
