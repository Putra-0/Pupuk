package com.setik.pupuk.ui_admin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.setik.pupuk.databinding.FragmentAddPupukBinding
import com.setik.pupuk.databinding.FragmentAddTebusBinding
import com.setik.pupuk.preferences
import com.setik.pupuk.ui_admin.data.Kios
import com.setik.pupuk.ui_admin.data.Pupuk
import com.setik.pupuk.ui_admin.model.PupukModel
import com.setik.pupuk.ui_kios.data.Tebus
import com.setik.pupuk.ui_kios.model.TebusKModel

class AddTebusFragment : DialogFragment() {

    private var _binding: FragmentAddTebusBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TebusKModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, com.google.android.material.R.style.Theme_AppCompat_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddTebusBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(TebusKModel::class.java)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null){
                "Berhasil menambahkan data"
            } else {
                "Gagal menambahkan data"
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            dismiss()

        })

        binding.btnTambahTebus.setOnClickListener{
            val nama_pupuk = binding.etNamaPupuk.text.toString().trim()
            val jumlah = binding.etJmlpupuk.text.toString().trim()
            val tgl = binding.etTgl.text.toString().trim()

            if (nama_pupuk.isEmpty()){
                binding.etNamaPupuk.error = "Nama pupuk tidak boleh kosong"
                binding.etNamaPupuk.requestFocus()
                return@setOnClickListener
            }
            if (jumlah.isEmpty()){
                binding.etJmlpupuk.error = "Jumlah pupuk tidak boleh kosong"
                binding.etJmlpupuk.requestFocus()
                return@setOnClickListener
            }
            if (tgl.isEmpty()){
                binding.etTgl.error = "Tanggal tidak boleh kosong"
                binding.etTgl.requestFocus()
                return@setOnClickListener
            }
            val kios = Kios()
            val tebus = Tebus()
            tebus.nama_pupuk = nama_pupuk
            tebus.jumlah = jumlah
            tebus.tanggal = tgl
            tebus.nama_kios = "Kios 1"
            viewModel.addTebus(tebus)

        }

    }
}