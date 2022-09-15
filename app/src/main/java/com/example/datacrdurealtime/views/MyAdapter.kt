package com.example.datacrdurealtime.views

import android.content.pm.ActivityInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.datacrdurealtime.databinding.ListaBinding
import com.example.datacrdurealtime.dto.Persona
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class MyAdapter() :
    ListAdapter<Persona, MyAdapter.HolderCrud>(DiffCallBack()) {

    inner class HolderCrud(private val bindingLista: ListaBinding) :
        RecyclerView.ViewHolder(bindingLista.root) {
        fun binder(persona: Persona?) {
            bindingLista.apply {
                this.tvId.text = persona?.id
                this.tvDesc.text = persona?.desc
                this.card.setOnClickListener {
                    Toast.makeText(this.card.context, persona?.id, Toast.LENGTH_SHORT).show()
                }
                this.btCancella.setOnClickListener {
                    if (!persona?.id.isNullOrEmpty()) {
                        cancelUser(tvId.text.toString(), this.tvDesc.text.toString())
                    } else {
                        Toast.makeText(it.context, "errroe ", Toast.LENGTH_SHORT).show()
                    }
                }
                this.btnUpdate.setOnClickListener {
                    updateUsers(tvId.text.toString(),"pippaaa")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCrud {
        val binding = ListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderCrud(binding)
    }

    override fun onBindViewHolder(holder: HolderCrud, position: Int) {
        val binding = getItem(position)
        holder.binder(binding)

    }

    fun cancelUser(user: String, desc: String) {
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("persona").child(user)
        myReference.removeValue()
    }
}

    fun updateUsers(userId:String,des:String) {
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("persona")

        val persona= mapOf(
            "id" to userId,
            "desc" to des
        )
        myReference.child(userId).updateChildren(persona).addOnCompleteListener {
        }
    }


class DiffCallBack() : DiffUtil.ItemCallback<Persona>() {
    override fun areItemsTheSame(oldItem: Persona, newItem: Persona) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Persona, newItem: Persona) = oldItem.id == newItem.id
}



