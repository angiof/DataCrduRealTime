package com.example.datacrdurealtime.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.datacrdurealtime.databinding.ListaBinding
import com.example.datacrdurealtime.dto.Persona

class MyAdapter() :
    ListAdapter<Persona, MyAdapter.HolderCrud>(DiffCallBack()) {

    inner class HolderCrud(private val bindingLista: ListaBinding) :
        RecyclerView.ViewHolder(bindingLista.root) {
        fun binder(persona: Persona?) {
            bindingLista.apply {
                this.tvId.text = persona?.id
                this.tvDesc.text= persona?.desc
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

}

class DiffCallBack() : DiffUtil.ItemCallback<Persona>() {
    override fun areItemsTheSame(oldItem: Persona, newItem: Persona) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Persona, newItem: Persona) = oldItem.id == newItem.id
}



