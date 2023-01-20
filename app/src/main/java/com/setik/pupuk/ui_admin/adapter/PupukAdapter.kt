package com.setik.pupuk.ui_admin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setik.pupuk.databinding.ListPupukBinding
import com.setik.pupuk.ui_admin.data.Pupuk

class PupukAdapter: RecyclerView.Adapter<PupukAdapter.ViewHolder>(){

    var pupuks = mutableListOf<Pupuk>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListPupukBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewNama.text = pupuks[position].nama_pupuk
        holder.binding.textViewStok.text = pupuks[position].stok
        holder.binding.textViewTgl.text = pupuks[position].tgl_masuk
    }

    override fun getItemCount(): Int {
        return pupuks.size
    }

    fun addPupuk(pupuk: Pupuk){
        if (!pupuks.contains(pupuk)){
            pupuks.add(pupuk)
        }else{
            val index=pupuks.indexOf(pupuk)
            if(pupuk.isDeleted){
                pupuks.removeAt(index)
            }else {
                pupuks[index] = pupuk
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListPupukBinding): RecyclerView.ViewHolder(binding.root){

    }

}