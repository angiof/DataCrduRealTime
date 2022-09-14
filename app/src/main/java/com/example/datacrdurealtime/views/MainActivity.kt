package com.example.datacrdurealtime.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.datacrdurealtime.R
import com.example.datacrdurealtime.dto.Persona
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var empList: ArrayList<Persona>  //lista del modello

    val db = FirebaseDatabase.getInstance().reference
    val myReference = db.child("persona") // reference patch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        passaSecodn()
        getLista()




    }

    fun getLista() {
        empList = arrayListOf()
        val listainer = object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                empList.clear()
                for (data in snap.children) {
                    val datiSnap =
                        data.getValue(Persona::class.java) //modella i risultati dell'iterazione dentro la classe modello
                    empList.add(datiSnap!!)   //aggiugne i dati della classe modello all'interno della lista
                }
                val myAdapter = MyAdapter()
                findViewById<RecyclerView>(R.id.recy_mian).apply {
                    this.setHasFixedSize(true)
                    myAdapter.submitList(empList)
                    this.adapter = myAdapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity, p0.message, Toast.LENGTH_SHORT).show()
            }
        }
        myReference.addValueEventListener(listainer)

    }

    fun setAdderIUSers() {
        findViewById<Button>(R.id.btn_main_adder).setOnClickListener {
            val nomeID = findViewById<EditText>(R.id.tx_nome).text.toString()
            val desc = findViewById<EditText>(R.id.tx_desc).text.toString()
            val nome: String = nomeID
            val descT: String = desc
            val oggpersona = Persona(nome, descT)

            val listainerAdder = db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    myReference.child(nome).setValue(oggpersona).addOnSuccessListener {
                    }.addOnFailureListener {

                        Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })

            myReference.addValueEventListener(listainerAdder)
        }
    }

    fun passaSecodn() {
        findViewById<Button>(R.id.passa1).setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))

        }
    }
}