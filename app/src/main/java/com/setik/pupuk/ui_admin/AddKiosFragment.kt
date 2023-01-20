package com.setik.pupuk.ui_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.setik.pupuk.databinding.FragmentAddKiosBinding
import com.setik.pupuk.databinding.FragmentAddPupukBinding
import com.setik.pupuk.ui_admin.data.Kios
import com.setik.pupuk.ui_admin.data.Pupuk
import com.setik.pupuk.ui_admin.model.KiosModel
import com.setik.pupuk.ui_admin.model.PupukModel

class AddKiosFragment : DialogFragment() {

    private var _binding: FragmentAddKiosBinding? = null
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
        _binding = FragmentAddKiosBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(KiosModel::class.java)

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

        binding.btnTambahKios.setOnClickListener {
            val nama_kios = binding.etNamaKios.text.toString().trim()
            val email = binding.etNamaEmail.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            val notelp = binding.etNoTelp.text.toString().trim()

            if (nama_kios.isEmpty()){
                binding.etNamaKios.error = "Nama kios tidak boleh kosong"
                binding.etNamaKios.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding.etNamaEmail.error = "Email tidak boleh kosong"
                binding.etNamaEmail.requestFocus()
                return@setOnClickListener
            }
            if (alamat.isEmpty()){
                binding.etAlamat.error = "Alamat tidak boleh kosong"
                binding.etAlamat.requestFocus()
                return@setOnClickListener
            }
            if (notelp.isEmpty()){
                binding.etNoTelp.error = "No Telp tidak boleh kosong"
                binding.etNoTelp.requestFocus()
                return@setOnClickListener
            }

            val kios = Kios()
            kios.nama = nama_kios
            kios.email = email
            kios.password = "123456"
            kios.alamat = alamat
            kios.no_telp = notelp

            viewModel.addKios(kios)
        }

    }
}