package com.example.datacrdurealtime.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
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

class MainActivity : AppCompatActivity() {
    private lateinit var empList: ArrayList<Persona>
    val db = FirebaseDatabase.getInstance().reference
    val range : IntRange =1..99
    val myReference = db.child("persona")
    private lateinit var viewModel: ViewModelsCrud
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val adapter :MyAdapter=MyAdapter()
        //viewModel = ViewModelProvider(this)[ViewModelsCrud::class.java]
        getLista()

    }
    fun getLista() {
        empList = arrayListOf()
        val listainer = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                empList.clear()
                for (ds in p0.children) {
                    val emptData = p0.getValue(Persona::class.java)

                empList.add(emptData!!)
                }
                val myAdapter:MyAdapter =MyAdapter()

                myAdapter.submitList(empList)
                findViewById<RecyclerView>(R.id.recy_mian).apply {
                     this.adapter = myAdapter
                     this.setHasFixedSize(true)
                 }

                Log.d("mario", empList.toString())
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity, p0.message, Toast.LENGTH_SHORT).show()
            }
        }
        myReference.addValueEventListener(listainer)

    }
}