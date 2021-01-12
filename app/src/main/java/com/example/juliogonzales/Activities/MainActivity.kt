package com.example.juliogonzales.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.juliogonzales.Auxiliaries.shortToast
import com.example.juliogonzales.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hide loading -- ocultar carga
        loadingSignIn.visibility = View.GONE

        var username: String
        var password: String

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        // Check if already logged in -- comprueba si ya a iniciado sesion
        if (currentUser != null) {
            // Go to question activity - Test
           // val intent = Intent(this, AllQuestionsActivity::class.java)
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()


        }

        login_btn.setOnClickListener {
            // loading wheel -- preogresbar cargando
            loadingSignIn.visibility = View.VISIBLE

            // Get user input -- obtener entrada de usuario
            username = username_edittxt.text.toString()
            password = pass_edittxt.text.toString()

            // check if there was input -- comprueba si hubo entrada
            if (username == "" || password == "") {
                shortToast("Please fill both fields", this)
                loadingSignIn.visibility = View.GONE
                return@setOnClickListener
            }

            // Login firebase
            mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        // Go questions activity
                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login failed
                        shortToast(it.exception!!.message!!, this)
                    }

                    loadingSignIn.visibility = View.GONE
                }
        }
    }
}
