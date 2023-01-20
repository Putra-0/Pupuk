package com.setik.pupuk.ui_admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setik.pupuk.databinding.ListTebusBinding
import com.setik.pupuk.ui_admin.data.Kios

import com.setik.pupuk.ui_kios.data.Tebus


class TebusAdapter: RecyclerView.Adapter<TebusAdapter.ViewHolder>() {

    var tebuss = mutableListOf<Tebus>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListTebusBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewNama.text = tebuss[position].nama_kios
        holder.binding.textViewTgl.text = tebuss[position].tanggal
        holder.binding.textViewStok.text = tebuss[position].nama_pupuk
        holder.binding.textViewNamak.text = tebuss[position].jumlah
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
    inner class ViewHolder(val binding: ListTebusBinding): RecyclerView.ViewHolder(binding.root){

    }

}