package com.example.juliogonzales.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.juliogonzales.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        createReport.setOnClickListener {
            val ir = Intent(this@WelcomeActivity, NewReportActivity::class.java)
            startActivity(ir)
            finish()
        }
        btnLastReports.setOnClickListener {
            val intent = Intent(this, LatestReportActivity::class.java)
            startActivity(intent)
        }

        print("---------------------DEBUG--------------------------------------")

    }
}
