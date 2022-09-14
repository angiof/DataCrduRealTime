package com.example.datacrdurealtime.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.datacrdurealtime.R
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        delate()
        goToHome()

    }

    private fun goToHome() {
        findViewById<Button>(R.id.passa).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))

        }
    }

    fun delate() {
        findViewById<Button>(R.id.btn_main2_querry).setOnClickListener {
            val db = FirebaseDatabase.getInstance().reference
            val myReference = db.child("persona")
            val id: String? = findViewById<EditText>(R.id.tx_desc_2).text.toString()
            val desc: String? = findViewById<EditText>(R.id.tx_id_2).text.toString()


            myReference.child(id.toString()).removeValue().addOnCompleteListener {
                Toast.makeText(this, "rimosso", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "erroee", Toast.LENGTH_SHORT).show()

            }
        }
    }

}