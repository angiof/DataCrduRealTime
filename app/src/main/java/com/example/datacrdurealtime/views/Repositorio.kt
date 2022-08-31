package com.example.datacrdurealtime.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
val db = FirebaseDatabase.getInstance().reference
val myReference = db.child("persona")
class Repositorio(val context: Context) {


    fun getLista() :MutableList<String?> {

        val persone: MutableList<String?> = ArrayList()
        val listainer = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0.children) {
                    val personeX = ds.key
                    persone.add(personeX)
                    //stampo la response
                   // Toast.makeText(context, persone.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, p0.message, Toast.LENGTH_SHORT).show()
            }
        }
        myReference.addListenerForSingleValueEvent(listainer)
        val listainer2= object :ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                Toast.makeText(context, "aggiunto nuovo valore su  ${p0.key} = ${p0.value}  ", Toast.LENGTH_SHORT).show()
                Log.d("mario", "aggiunto nuovo valore su  ${p0.key} = ${p0.value}  ",)
                Log.d("mario",  p0.toString())
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Toast.makeText(context, "cambiata la key angelo ${p0.key} nuovo valore ${p0.value} ", Toast.LENGTH_SHORT).show()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        }
        myReference.addChildEventListener(listainer2)
        return persone
    }






}