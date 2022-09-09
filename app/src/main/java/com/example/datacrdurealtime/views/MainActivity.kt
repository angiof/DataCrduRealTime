package com.example.datacrdurealtime.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.datacrdurealtime.R
import com.example.datacrdurealtime.dto.Persona
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var empList: ArrayList<Persona>
    val db = FirebaseDatabase.getInstance().reference
    val myReference = db.child("persona")
    private lateinit var viewModel: ViewModelsCrud
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLista()

        findViewById<Button>(R.id.btn_main_adder).setOnClickListener {
            val nomeID= findViewById<EditText>(R.id.tx_nome).text.toString()
            val nome:String =nomeID
            val oggpersona= Persona(nome,"mursia")

            val listainerAdder = db.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {


                    myReference.child(nome).setValue(oggpersona).addOnSuccessListener {
                    }.addOnFailureListener {

                        Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
            myReference.addValueEventListener(listainerAdder)




            }



    }

    fun getLista() {
        empList = arrayListOf()

        val listainer = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                empList.clear()

                for (data in p0.children) {
                    val datiSnap = data.getValue(Persona::class.java)
                    empList.add(datiSnap!!)
                }
                Log.d("mariotto", empList.toString())

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

    fun setBtn() {
        val numeroRange : LongRange = 0..92382938967865
        val myReference = db.child("persona").child("99")

        val listainerAdder = db.addValueEventListener(object :ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {

               val oggpersona= Persona("polo","mursia")
               findViewById<Button>(R.id.btn_main_adder).setOnClickListener {

                  myReference.setValue(oggpersona).addOnSuccessListener {

                  }
               }

           }

           override fun onCancelled(error: DatabaseError) {
           }

       })
        myReference.addValueEventListener(listainerAdder)
    }

}