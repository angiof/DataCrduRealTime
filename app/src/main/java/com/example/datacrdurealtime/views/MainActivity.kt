package com.example.datacrdurealtime.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.datacrdurealtime.R
import com.example.datacrdurealtime.dto.Persona
import com.example.datacrdurealtime.dto.Users
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.AuthProvider
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var empList: ArrayList<Persona>  //lista del modello
    private lateinit var firebaseAuthStateListainer: FirebaseAuth.AuthStateListener

    companion
    object {
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("persona") // reference patch
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        integrateAU()
        exit()

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
            val nome: String = nomeID
            val descT: String = desc
            if (nome.isEmpty() or descT.isEmpty() or snap.hasChild(nome)) {
                Toast.makeText(this, "non rompere il progetto ", Toast.LENGTH_SHORT).show()
            } else {
                val oggpersona = Persona(nome, descT)
                myReference.child(nome).setValue(oggpersona).addOnSuccessListener {
                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
                }
            }

        }
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
                    findViewById<TextView>(R.id.tx_id_user).setText(user.email + user.displayName + user.phoneNumber + user.providerId)
                    createUser(user.displayName.toString(),user.email.toString())
                }
            }
        }.launch(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider)
                .build()
        )
    }

    fun createUser(user: String, email: String) {
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("users").child(user) //
        val users = Users(user, email)
        myReference.setValue(users)
    }

    fun exit() {
        findViewById<Button>(R.id.btn_exit).setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
                Toast.makeText(this, "chiuso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
