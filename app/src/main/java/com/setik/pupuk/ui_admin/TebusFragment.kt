package com.setik.pupuk.ui_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.setik.pupuk.databinding.FragmentTebusBinding
import com.setik.pupuk.ui_admin.adapter.TebusAdapter
import com.setik.pupuk.ui_kios.adapter.TebusKAdapter
import com.setik.pupuk.ui_kios.model.TebusKModel

class TebusFragment : Fragment() {

    private var _binding: FragmentTebusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = TebusAdapter()
    private lateinit var viewModel: TebusKModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentTebusBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(TebusKModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTebus.adapter = adapter
        viewModel.tebus.observe(viewLifecycleOwner, Observer{
            adapter.addTebus(it)
        })

        viewModel.getRealtimeUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}