package com.example.juliogonzales.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.juliogonzales.Models.LastReportModel
import com.example.juliogonzales.Models.ReportModel
import com.example.juliogonzales.R
import com.example.juliogonzales.Utils.LocationUtils
import com.google.android.gms.location.*
import com.google.firebase.database.*
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_new_report.*
import kotlinx.android.synthetic.main.activity_report_summary.*
import kotlinx.android.synthetic.main.activity_send_report.*
import kotlinx.android.synthetic.main.activity_send_report.createNewReport
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class SendReportActivity: AppCompatActivity()  {



    private val context: Context? = null
    var reportId: String = ""
    var mail: String = ""
    var date: String = ""

    // email information
    var personArrival: String =""
    var sealNumber: String = ""
    var personAfixed: String = ""
    var personVerified: String = ""
    var txtLat:String = ""
    var txtLong:String = ""


    //to get the location -- para obtener la ubicación
    private var locationManager : LocationManager? = null

    private lateinit var reportReference: DatabaseReference
    private lateinit var Question1Reference: DatabaseReference

    private val MONTHS = arrayOf("January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December")
    private var orderDate: Date? = null

//    LocationID
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_report)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()

        // Get the id of the Report-- Obtenga la identificación del informe
        val bundle = intent.extras
        reportId = bundle?.get("form") as String
        mail = bundle.get("mail") as String



        createNewReport.setOnClickListener {
            personArrival = txtPersonArrival.text.toString().trim()
            sealNumber = txtSealNumbers.text.toString().trim()
            personAfixed = txtPersonAfixed.text.toString().trim()
            personVerified = txtPersonAfixed.text.toString().trim()

           // personVerified = txtPersonVerified.text.toString().trim()

            if(personArrival.isEmpty() && sealNumber.isEmpty() && personAfixed.isEmpty() && personVerified.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }


            saveReport()

            val intent = Intent(this, SignActivity::class.java)
            intent.putExtra("reportID", reportId)
            intent.putExtra("date",date)
            intent.putExtra("mail",mail)
            intent.putExtra("personArrival", personArrival)
            intent.putExtra("sealNumber",sealNumber)
            intent.putExtra("personAfixed",personAfixed)
            intent.putExtra("personVer",personVerified)
            startActivity(intent)
            finish()
        }

        btnAddEvidence.setOnClickListener {
            val intent = Intent(this@SendReportActivity, FinalImagesReportActivity::class.java)
            intent.putExtra("editar",true)
            intent.putExtra("reportID", reportId)
            startActivity(intent)
        }




            openDatePicker()


//        saveReport()

    }



    override fun onStart() {
        loadInformation()
        super.onStart()
    }

    fun loadInformation(){
        // Initialize Database
        Question1Reference = FirebaseDatabase.getInstance().reference
            .child("Reportes")
            .child(reportId)
            .child("form")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(ReportModel::class.java)
                // [START_EXCLUDE]
                post?.let {

                    val propertyOf = it.propertyName
                    val vehicleNumber = it.vehicleNumber
                    val licence = it.licence
                    val personSecurity = it.securityPerson
                    val email = it.email



                    txtReportId.text = it.id
                    txtPropertyOf.text = propertyOf
                    txtVehicleNumberSendReport.text = vehicleNumber
                    txtLicenceSendReport.text = licence
                    txtSecurityPersonSendReport.text = personSecurity
                    txtEmailReportSendReport.text = email




                    Log.d("DEBUGTXT",it.propertyName )

                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        Question1Reference.addValueEventListener(postListener)
    }

    private fun openDatePicker() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        date = currentDate
        txtDateDetail.text = currentDate
    }

    fun saveReport(){
        reportReference = FirebaseDatabase.getInstance().getReference("Reportes").child(reportId).child("form")
        val date = txtDateDetail.text.toString().trim()

        reportReference.child("date").setValue(date).addOnCompleteListener{
            Toast.makeText(this,"Reporte Creado Correctamente", Toast.LENGTH_SHORT).show()
        }
        reportReference.child("personArrival").setValue(personArrival).addOnCompleteListener{
        }
        reportReference.child("sealNumber").setValue(sealNumber).addOnCompleteListener{
        }
        reportReference.child("personAfixed").setValue(personAfixed).addOnCompleteListener{
        }
        reportReference.child("personVerified").setValue(personVerified).addOnCompleteListener{
        }
        reportReference.child("lat").setValue(txtLat).addOnCompleteListener{
        }
        reportReference.child("long").setValue(txtLong).addOnCompleteListener{
        }



        
    }




    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        txtLat = location.latitude.toString()
                        txtLong = location.longitude.toString()
                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            val lat = mLastLocation.latitude.toString()
            val lon = mLastLocation.longitude.toString()
            findViewById<TextView>(R.id.latTextView).text = "Lat: $lat"
            findViewById<TextView>(R.id.lonTextView).text = "Lon: $lon"
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }




}
