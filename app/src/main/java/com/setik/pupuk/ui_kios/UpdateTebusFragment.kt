package com.setik.pupuk.ui_kios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.setik.pupuk.databinding.FragmentUpdateTebusBinding
import com.setik.pupuk.ui_kios.data.Tebus
import com.setik.pupuk.ui_kios.model.TebusKModel

class UpdateTebusFragment(private val tebus: Tebus) : DialogFragment() {

    private var _binding: FragmentUpdateTebusBinding? = null
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
        _binding = FragmentUpdateTebusBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(TebusKModel::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)

        binding.etNamaPupuk.setText(tebus.nama_pupuk)
        binding.etJmlpupuk.setText(tebus.jumlah)
        binding.etTgl.setText(tebus.tanggal)

        binding.btnUpdateTebus.setOnClickListener {
            val namaa_pupuk = binding.etNamaPupuk.text.toString().trim()
            val jml_pupuk = binding.etJmlpupuk.text.toString().trim()
            val tgl = binding.etTgl.text.toString().trim()

            if (namaa_pupuk.isEmpty()){
                binding.etNamaPupuk.error = "Nama pupuk tidak boleh kosong"
                return@setOnClickListener
            }
            if (jml_pupuk.isEmpty()){
                binding.etJmlpupuk.error = "Jumlah pupuk tidak boleh kosong"
                return@setOnClickListener
            }
            if (tgl.isEmpty()){
                binding.etTgl.error = "Tanggal tidak boleh kosong"
                return@setOnClickListener
            }

            val tebus = Tebus()
            tebus.nama_pupuk = namaa_pupuk
            tebus.jumlah = jml_pupuk
            tebus.tanggal = tgl

            viewModel.updateTebus(tebus)
            dismiss()
            Toast.makeText(context, "Data berhasil diubah", Toast.LENGTH_SHORT).show()

        }


    }
}