package com.setik.pupuk.ui_kios.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setik.pupuk.databinding.ListPupukBinding

import com.setik.pupuk.ui_kios.data.Tebus

class TebusKAdapter: RecyclerView.Adapter<TebusKAdapter.ViewHolder>() {

    var tebuss = mutableListOf<Tebus>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListPupukBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewNama.text = tebuss[position].nama_pupuk
        holder.binding.textViewStok.text = tebuss[position].jumlah
        holder.binding.textViewTgl.text = tebuss[position].tanggal
    }

    fun addTebus(tebus: Tebus){
        if (!tebuss.contains(tebus)){
            tebuss.add(tebus)
        }else{
            val index=tebuss.indexOf(tebus)
            if(tebus.isDeleted){
                tebuss.removeAt(index)
            }else {
                tebuss[index] = tebus
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tebuss.size
    }
    inner class ViewHolder(val binding: ListPupukBinding): RecyclerView.ViewHolder(binding.root){

    }

}