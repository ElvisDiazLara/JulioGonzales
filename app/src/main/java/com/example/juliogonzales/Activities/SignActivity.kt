package com.example.juliogonzales.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.juliogonzales.Models.ChecklistModel
import com.example.juliogonzales.Models.ReportModel
import com.example.juliogonzales.R
import com.example.juliogonzales.Utils.DrawingView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_last_report_summary.view.*
import kotlinx.android.synthetic.main.activity_report_summary.*
import kotlinx.android.synthetic.main.activity_report_summary.view.*
import kotlinx.android.synthetic.main.activity_sign.*
import org.jetbrains.anko.find
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SignActivity : AppCompatActivity() {




    internal var storage: FirebaseStorage? = null
    private var sharePath: Uri? = null
    lateinit var pic: File
    var UriMail: Uri? = null
    internal var storageReference: StorageReference? = null
    private lateinit var Question1Reference: DatabaseReference
    private val images: MutableList<String> = ArrayList()


    //Data to Send Email-- Datos para enviar correo electrónico
    var reportId: String = ""
    var date: String = ""
    var mail: String = ""
    var imageUri: Uri? = null
    var imageUrl: String = ""
    var personArrival: String = ""
    var sealNumber: String = ""
    var personAfixed: String = ""
    var personVerified: String = ""
    //var question1: String = txtQuestion1.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val drawingView = findViewById<DrawingView>(R.id.drawingView)
        val btn_ready = findViewById<LinearLayout>(R.id.btn_listo)
        val btn_trash = findViewById<ImageView>(R.id.btn_trash)






        getImages()
        imageUri

        // Get the id of the Report -- Obtenga la identificación del informe
        val bundle = intent.extras
        reportId = bundle?.get("reportID") as String
        date = bundle.get("date") as String
        mail = bundle.get("mail") as String
        personArrival = bundle.get("personArrival") as String
        sealNumber = bundle.get("sealNumber") as String
        personAfixed = bundle.get("personAfixed") as String
        personVerified = bundle.get("personVer") as String
       //question1 = bundle.get("question_1") as String




        Log.d("DEBUGIDSIGN", reportId)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference



        drawingView.setOnTouchListener { _, _ ->
            btn_ready.visibility = View.VISIBLE
            /* show dialog here */
            false
        }

        btn_trash.setOnClickListener {
            drawingView.clearCanvas()
            btn_ready.visibility = View.GONE
        }

        btn_ready?.setOnClickListener {
            btn_ready.visibility = View.GONE
            takeScreenshot()

        }

        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("checklist")
            .child("question1")
            .child("question2")
        val postListener = object {
            fun onDataChange(dataSnapshot: DataSnapshot){
                val post = dataSnapshot.getValue(ChecklistModel::class.java)
                 post?.let {
                     txtQuestion1.text = it.optionSelect
                     txtQuestion2.text = it.optionSelect
                 }
            }
        }


    }


    override fun onRestart() {
        val intent = Intent(this@SignActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
        super.onRestart()
    }

    private fun takeScreenshot() {
        layoutLine.visibility = View.GONE
        layoutUp.visibility = View.GONE
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        try {
            // image naming and path  to include sd card  appending name you choose for file -- nombre de la imagen y ruta para incluir la tarjeta sd agregando el nombre que elija para el archivo
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg"

            // create bitmap screen capture -- crear captura de pantalla de mapa de bits
            val v1 = window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false

            val imageFile = File(mPath)

            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            //setting screenshot in imageview -- configuración de captura de pantalla en imageview
            val filePath = Uri.fromFile(imageFile)
            UriMail = filePath

            val ssbitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            iv!!.setImageBitmap(ssbitmap)
            sharePath = filePath
            uploadFile()
        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM -- Pueden aparecer varios errores con el manejo de archivos o DOM
            e.printStackTrace()
        }
    }

    private fun uploadFile() {
        if (sharePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val imageRef = storageReference!!.child(
                "images/$reportId/Signature/" + UUID.randomUUID().toString()
            )
            imageRef.putFile(sharePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    sendPdf()
                    sendEmail()
                    Toast.makeText(this, "Sign Uploaded", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this@SignActivity, WelcomeActivity::class.java)
//                    startActivity(intent)
//                    finish()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()

                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                }
        }
    }


    fun getImages() {

        // Get the id of the Report -- Obtenga la identificación del informe
        val bundle = intent.extras
        val id = bundle?.get("reportID") as String

        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child("images/$id/extras")
        // WORKING CODE!
        // Create a reference to a file from a Google Cloud Storage URI -- Crea una referencia a un archivo desde un URI de Google Cloud Storage

        val storageReference = FirebaseStorage.getInstance().reference

        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { prefix ->
                    Log.d("Buenoprefix", prefix.toString())
                }

                listResult.items.forEach { item ->

                    images.add(item.toString())
                    val gsReference = storage.getReferenceFromUrl(item.toString())
                    gsReference.downloadUrl.addOnSuccessListener {
                        if (it != null) {
                            imageUrl = it.toString()
                        }
                    }
                    Log.d("Bueno1", images.toString())
                    Log.d("Bueno2", item.toString())
                    Log.d("Bueno3", imageUrl)
                }

            }
            .addOnFailureListener {
                Log.d("Bueno", "Error")
            }

    }

    fun sendPdf(){
        //Create pdf


        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("form")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI -- Obtenga el objeto Post y use los valores para actualizar la interfaz de usuario

                val post = dataSnapshot.getValue(ReportModel::class.java)
                // [START_EXCLUDE]
                post?.let {

                    var str = images.toString()
                    var delimiter1 = ","
                    val parts = str.split(delimiter1)

                    Log.d("DEBUGPARTS", parts.toString())

                    val listImages = UriMail.toString()
                    val icon = getDrawable(R.drawable.image)
                    val propertyOf = it.propertyName
                    val vehicleNumber = it.vehicleNumber
                    val licence = it.licence
                    val personSecurity = it.securityPerson
                    val email = it.email
                    val uri = Uri.parse(sharePath.toString())


                    //Intent to send the email with the information -- Intento enviar el correo electrónico con la información
                    val mDoc = Document()
                    val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
                    val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
                    val stringFile = Environment.getExternalStorageDirectory()
                        .path + File.separator + mFileName + ".pdf"
                    try {
                        PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
                        mDoc.open()
                        val mTextReportId = reportId
                        val mTextPropertyOf = propertyOf
                        val mTextVehicleNumber = vehicleNumber
                        val mTextLicence = licence
                        val mTextPersonSecurity = personSecurity
                        val mTextEmail = email
                        val mTextDate = date
                        val mTextPersonArrival = personArrival
                        val mTextSealNumber = sealNumber
                        val mTextPersonAfixed = personAfixed
                        val mTextPersonVerified = personVerified
                       /* val mTextQuestion1 =
                        val mTextQuestion2 =
                        val mTextQuestion3 =
                        val mTextQuestion4 =
                        val mTextQuestion5 =
                        val mTextQuestion6 =
                        val mTextQuestion7 =
                        val mTextQuestion8 =
                        val mTextQuestion9 =
                        val mTextQuestion10 =
                        val mTextQuestion11 =
                        val mTextQuestion12 =
                        val mTextQuestion13 =
                        val mTextQuestion14 =
                        val mTextQuestion15 =
                        val mTextQuestion16 =
                        val mTextQuestion17 =
                        val mTextQuestion18 =
                        val mTextQuestion19 =*/

                        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, UriMail)

                        val width = imageBitmap.width;
                        val height = imageBitmap.height;
                        val scaleWidth = (width * 0.25f).toInt()
                        val scaleHeight = (height * 0.25f).toInt()
                        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, scaleWidth, scaleHeight, true)
                        val stream = ByteArrayOutputStream()
                        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val imageToPdf = Image.getInstance(stream.toByteArray())

                        mDoc.add(Paragraph  ("Report No.: \n " + mTextReportId))
                        mDoc.add(Paragraph  ("Load Property of: \n " + mTextPropertyOf))
                        mDoc.add(Paragraph  ("Vehicle's Economic Number: \n " + mTextVehicleNumber))
                        mDoc.add(Paragraph  ("Licence plate: \n " + mTextLicence))
                        mDoc.add(Paragraph  ("Printed name of person who conducted security inspection upon arrival: \n " + mTextPersonSecurity))
                        mDoc.add(Paragraph  ("Email where the report will send: \n " + mTextEmail))
                        mDoc.add(Paragraph  ("Date: \n " + mTextDate))
                        mDoc.add(Paragraph  ("Printed name of person who conducted follow up security inspection: \n " + mTextPersonArrival))
                        mDoc.add(Paragraph  ("Seal number(s) that was on container when it arrived at this facility: \n " + mTextSealNumber))
                        mDoc.add(Paragraph  ("Printed name of person who affixed seal(s): \n " + mTextPersonAfixed))
                        mDoc.add(Paragraph  ("Printed name of person who affixed seal(s): \n " + mTextPersonVerified))
                        mDoc.add(Paragraph  (" \n " ))
                        mDoc.add(Paragraph  ("1.Bumpers \n"))
                        mDoc.add(Paragraph  ("2.Engine \n"))
                        mDoc.add(Paragraph  ("3.Tires \n"))
                        mDoc.add(Paragraph  ("4.Floor (Truck) \n"))
                        mDoc.add(Paragraph  ("5.Fuel Tanks \n"))
                        mDoc.add(Paragraph  ("6.Cab \n"))
                        mDoc.add(Paragraph  ("7.Air Tanks \n"))
                        mDoc.add(Paragraph  ("8.Drive Shafts \n"))
                        mDoc.add(Paragraph  ("9.Fifth Wheel \n"))
                        mDoc.add(Paragraph  ("10.Outside/Undercarriage \n"))
                        mDoc.add(Paragraph  ("11.Outside/Inside Doors \n"))
                        mDoc.add(Paragraph  ("12.Floor inside the trailer \n"))
                        mDoc.add(Paragraph  ("13.Sidewalls \n"))
                        mDoc.add(Paragraph  ("14.Frontwall \n"))
                        mDoc.add(Paragraph  ("15.Roof \n"))
                        mDoc.add(Paragraph  ("16.Refrigeration \n"))
                        mDoc.add(Paragraph  ("17.Bumpers \n"))
                        mDoc.add(Paragraph  ("18.Contamination \n"))
                        mDoc.add(Paragraph  ("19.Mechanisms \n"))
                        //  mDoc.add(Paragraph  ("Images: \n " + ))
                        mDoc.add(imageToPdf)
                        mDoc.close()



                        Toast.makeText(applicationContext, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
                    }catch (e: Exception){
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                    }



                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }

        }


        Question1Reference.addValueEventListener(postListener)




    }




    fun sendEmail() {
        // Initialize Database -- Inicializar base de datos

        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("form")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI -- Obtenga el objeto Post y use los valores para actualizar la interfaz de usuario
                val post = dataSnapshot.getValue(ReportModel::class.java)
                // [START_EXCLUDE]
                post?.let {

                    var str = images.toString()
                    var delimiter1 = ","
                    val parts = str.split(delimiter1)

                    Log.d("DEBUGPARTS", parts.toString())

                    val listImages = UriMail.toString()
                    val icon = getDrawable(R.drawable.image)
                    val propertyOf = it.propertyName
                    val vehicleNumber = it.vehicleNumber
                    val licence = it.licence
                    val personSecurity = it.securityPerson
                    val email = it.email
                    val uri = Uri.parse(sharePath.toString())


                    //Intent to send the email with the information -- Intento enviar el correo electrónico con la información
                    val emailintent = Intent(Intent.ACTION_SEND)
                    emailintent.data = Uri.parse("mailto:")
                    emailintent.type = "plain/text"
                    var strTo = arrayOf(mail)
                    emailintent.putExtra(Intent.EXTRA_EMAIL, strTo)
                    emailintent.putExtra(Intent.EXTRA_SUBJECT, "Reporte No. $reportId")
                    emailintent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Load Property of: \n" +
                                " $propertyOf\n" +
                                "\n" +
                                "Vehicle's Economic Number\n" +
                                " $vehicleNumber\n" +
                                "\n" +
                                "Licence plate\n" +
                                " $licence\n" +
                                "\n" +
                                "Printed name of person who conducted security inspection upon arrival:\n" +
                                " $personSecurity\n" +
                                "\n" +
                                "Email where the report will send\n" +
                                " $email\n" +
                                "\n" +
                                "Date\n" +
                                " $date\n" +
                                "\n" +
                                "Printed name of person who conducted follow up security inspection:\n" +
                                " $personArrival\n" +
                                "\n" +
                                "Seal number(s) that was on container when it arrived at this facility:\n" +
                                " $sealNumber\n" +
                                "\n" +
                                "Printed name of person who affixed seal(s):\n" +
                                " $personAfixed\n" +
                                "\n" +
                                "Printed name of person who verified physical integrity of seal(s):\n" +
                                " $personVerified\n" +
                                "\n" +
                                "Images: \n" +
                                //" ${Question1Reference.database.getReference(txtQuestion1.toString())}\n" +
                                "\n"
                    )
//                   emailintent.putExtra(Intent.EXTRA_STREAM,sharePath)
                    intent.putExtra(Intent.EXTRA_STREAM, sharePath)
                    Log.d("BuenoImageUrl", imageUrl)

                    if (emailintent.resolveActivity(this@SignActivity.packageManager) != null) {
                        startActivity(Intent.createChooser(emailintent, ""))
                    }

                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }



        }

        
        Question1Reference.addValueEventListener(postListener)


       /* val outputFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "example.pdf"
        )
        val uri = Uri.fromFile(outputFile)

        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.type = "application/pdf"
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.setPackage("com.whatsapp")

        startActivity(share)*/




    }













}
