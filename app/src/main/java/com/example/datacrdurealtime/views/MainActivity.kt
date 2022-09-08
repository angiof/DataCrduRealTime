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
    val myReference = db.child("persona")
    private lateinit var viewModel: ViewModelsCrud
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLista()

    }
    fun getLista() {
        empList = arrayListOf()



        val listainer = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                empList.clear()

                for (data in p0.children){
                    val datiSnap=data.getValue(Persona::class.java)
                    empList.add(datiSnap!!)
                }
                Log.d("mariotto",empList.toString())

                val myAdapter:MyAdapter =MyAdapter()

                findViewById<RecyclerView>(R.id.recy_mian).apply {
                    this.setHasFixedSize(true)
                    myAdapter.submitList(empList)
                    this.adapter = myAdapter
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