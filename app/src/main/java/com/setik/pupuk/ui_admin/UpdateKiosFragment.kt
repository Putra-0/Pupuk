package com.setik.pupuk.ui_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.setik.pupuk.databinding.FragmentUpdateKiosBinding
import com.setik.pupuk.databinding.FragmentUpdatePupukBinding
import com.setik.pupuk.ui_admin.data.Kios
import com.setik.pupuk.ui_admin.model.KiosModel
import com.setik.pupuk.ui_admin.model.PupukModel

class UpdateKiosFragment(private val kios: Kios) : DialogFragment() {

    private var _binding: FragmentUpdateKiosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: KiosModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, com.google.android.material.R.style.Theme_AppCompat_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUpdateKiosBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(KiosModel::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)

        binding.etNamaKios.setText(kios.nama)
        binding.etNamaEmail.setText(kios.email)
        binding.etAlamat.setText(kios.alamat)
        binding.etNoTelp.setText(kios.no_telp)

        binding.btnUpdateKios.setOnClickListener {
            val namaa_kios = binding.etNamaKios.text.toString().trim()
            val email = binding.etNamaEmail.text.toString().trim()
            val alamatt = binding.etAlamat.text.toString().trim()
            val notelpp = binding.etNoTelp.text.toString().trim()

            if (namaa_kios.isEmpty()){
                binding.etNamaKios.error = "Nama kios tidak boleh kosong"
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding.etNamaEmail.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (alamatt.isEmpty()){
                binding.etAlamat.error = "Alamat tidak boleh kosong"
                return@setOnClickListener
            }
            if (notelpp.isEmpty()){
                binding.etNoTelp.error = "No telp tidak boleh kosong"
                return@setOnClickListener
            }

            val kios = Kios()
            kios.nama = namaa_kios
            kios.email = email
            kios.alamat = alamatt
            kios.no_telp = notelpp
            
            viewModel.updateKios(kios)
            dismiss()
            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()

        }

    }
}