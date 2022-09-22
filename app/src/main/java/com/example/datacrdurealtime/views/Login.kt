package com.example.datacrdurealtime.views

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.datacrdurealtime.R
import com.example.datacrdurealtime.dto.Users
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }



    fun googleLogin() {
        findViewById<Button>(R.id.btn_login).setOnClickListener {
            integrateAU()
        }
    }

    fun createUser(user: String, email: String) {
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("users").child(user) //
        val users = Users(user, email)
        myReference.setValue(users)
    }
    @SuppressLint("SetTextI18n")
    fun integrateAU() {
        val provider = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = IdpResponse.fromResultIntent(it.data)
            if (it.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    //Toast.makeText(this, "ciaooo", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "ecco", Toast.LENGTH_SHORT).show()
                    findViewById<TextView>(R.id.tx_id_user).text = user.email + user.displayName + user.phoneNumber + user.providerId
                    createUser(user.displayName.toString(), user.email.toString())
                }
            }
        }.launch(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider)
                .build()
        )
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        googleLogin()
    }
}