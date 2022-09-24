package com.example.datacrdurealtime.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    companion object{
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("persona") // reference patch
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLista()
    }

    fun getLista() {
        empList = arrayListOf()
        val listainer = object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                empList.clear()
                for (data in snap.children) {
                    val datiSnap =
                        data.getValue(Persona::class.java)
                    empList.add(datiSnap!!)
                    setAdderIUSers(snap)
                }
                val myAdapter = MyAdapter()
                findViewById<RecyclerView>(R.id.recy_mian).apply {
                    this.setHasFixedSize(true)
                    this.adapter = myAdapter
                    myAdapter.submitList(empList)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        }
        myReference.addValueEventListener(listainer)
    }

    fun setAdderIUSers(snap: DataSnapshot) {
        findViewById<Button>(R.id.btn_main_adder).setOnClickListener {
            val nomeID = findViewById<EditText>(R.id.tx_nome).text.toString()
            val desc = findViewById<EditText>(R.id.tx_desc).text.toString()
            val id = findViewById<EditText>(R.id.tx_id).text.toString()
            val nome: String = nomeID
            val descT: String = desc
            val id_alpha: String = id
            if (nome.isEmpty()or descT.isEmpty() or snap.hasChild(nome) or snap.hasChild(id_alpha)){
                Toast.makeText(this, "non rompere il progetto ", Toast.LENGTH_SHORT).show()
            }else{
                val oggpersona = Persona( id_alpha,descT,nome)
                myReference.child(id_alpha).setValue(oggpersona).addOnSuccessListener {
                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {

            Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
