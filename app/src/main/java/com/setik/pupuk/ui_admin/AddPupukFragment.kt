package com.setik.pupuk.ui_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.setik.pupuk.databinding.FragmentAddPupukBinding
import com.setik.pupuk.ui_admin.data.Pupuk
import com.setik.pupuk.ui_admin.model.PupukModel

class AddPupukFragment : DialogFragment() {

    private var _binding: FragmentAddPupukBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PupukModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, com.google.android.material.R.style.Theme_AppCompat_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddPupukBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(PupukModel::class.java)

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

        binding.btnTambahPupuk.setOnClickListener {
            val namaa_pupuk = binding.etNamaPupuk.text.toString().trim()
            val stokk = binding.etStokPupuk.text.toString().trim()
            val tgl = binding.etTgl.text.toString().trim()

            if (namaa_pupuk.isEmpty()){
                binding.etNamaPupuk.error = "Nama pupuk tidak boleh kosong"
                binding.etNamaPupuk.requestFocus()
                return@setOnClickListener
            }
            if (stokk.isEmpty()){
                binding.etStokPupuk.error = "Stok pupuk tidak boleh kosong"
                binding.etStokPupuk.requestFocus()
                return@setOnClickListener
            }
            if (tgl.isEmpty()){
                binding.etTgl.error = "Tanggal tidak boleh kosong"
                binding.etTgl.requestFocus()
                return@setOnClickListener
            }

            val pupuk = Pupuk()
            pupuk.nama_pupuk = namaa_pupuk
            pupuk.stok = stokk
            pupuk.tgl_masuk = tgl

            viewModel.addPupuk(pupuk)
        }

    }
}