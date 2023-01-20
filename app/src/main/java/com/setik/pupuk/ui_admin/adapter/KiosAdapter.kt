package com.setik.pupuk.ui_admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setik.pupuk.databinding.ListPupukBinding
import com.setik.pupuk.ui_admin.data.Kios

class KiosAdapter:RecyclerView.Adapter<KiosAdapter.ViewHolder>() {

    var kioss = mutableListOf<Kios>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListPupukBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewNama.text = kioss[position].nama
        holder.binding.textViewStok.text = kioss[position].alamat
        holder.binding.textViewTgl.text = kioss[position].no_telp
    }

    override fun getItemCount(): Int {
        return kioss.size
    }

    fun addKios(kios: Kios){
        if (!kioss.contains(kios)){
            kioss.add(kios)
        }else{
            val index=kioss.indexOf(kios)
            if(kios.isDeleted){
                kioss.removeAt(index)
            }else {
                kioss[index] = kios
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListPupukBinding): RecyclerView.ViewHolder(binding.root){

    }

}