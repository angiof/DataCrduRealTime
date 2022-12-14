package com.example.datacrdurealtime.views

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.datacrdurealtime.R
import com.example.datacrdurealtime.databinding.ListaBinding
import com.example.datacrdurealtime.dto.Persona
import com.google.firebase.database.FirebaseDatabase

class MyAdapter() :
    ListAdapter<Persona, MyAdapter.HolderCrud>(DiffCallBack()) {
        //spero funzioni aiutami dio
    inner class HolderCrud(private val bindingLista: ListaBinding) :
        RecyclerView.ViewHolder(bindingLista.root) {
        fun binder(persona: Persona?) {
            bindingLista.apply {

                this.tvNome.text = persona?.nome
                this.tvDesc.text = persona?.desc
                this.card.setOnClickListener {
                    Toast.makeText(this.card.context, persona?.id, Toast.LENGTH_SHORT).show()
                }
                this.btCancella.setOnClickListener {
                    if (!persona?.id.isNullOrEmpty()or (persona?.id.toString() == "")) {
                        cancelUser(persona?.id.toString(), it.context)
                    } else {
                        Toast.makeText(it.context, "errroe ", Toast.LENGTH_SHORT).show()
                    }
                }
                this.btnUpdate.setOnClickListener {
                    updateUsers(persona?.id.toString(), it.context)
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

    fun cancelUser(id: String, ctx: Context) {
        val db = FirebaseDatabase.getInstance().reference
        val myReference = db.child("persona")
            myReference.child(id).removeValue()
            .addOnFailureListener {
            Toast.makeText(ctx, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun updateUsers(userId: String, ctx: Context) {
    val viewDialog: View = View.inflate(ctx, R.layout.custom, null)
    val builder = AlertDialog.Builder(ctx)
    builder.setView(viewDialog)
    val dialog= builder.create()
    viewDialog.findViewById<Button>(R.id.btn_update_dialog).setOnClickListener {
        val nome=viewDialog.findViewById<EditText>(R.id.tx_nome_update).text.toString()
        val desc=viewDialog.findViewById<EditText>(R.id.tx_desc_updt).text.toString()
        if (desc.isNullOrEmpty() and nome.isNullOrEmpty()){
            Toast.makeText(ctx, "daje metti qualcosa ", Toast.LENGTH_SHORT).show()
        }else{
            val mapPerson = mapOf(
                "id" to userId,
                "desc" to desc,
                "nome" to nome
            )
            MainActivity.myReference.child(userId)
                .updateChildren(mapPerson).addOnCompleteListener {
                Toast.makeText(ctx, "ok aggiornato", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }
    if (viewDialog.parent!=null){
        (viewDialog.parent as ViewGroup).removeView(viewDialog)
    }
    dialog.show()
}


class DiffCallBack() : DiffUtil.ItemCallback<Persona>() {
    override fun areItemsTheSame(oldItem: Persona, newItem: Persona) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Persona, newItem: Persona) = oldItem.id == newItem.id
}



