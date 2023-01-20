package com.setik.pupuk.ui_admin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.setik.pupuk.databinding.FragmentKiosBinding
import com.setik.pupuk.ui_admin.adapter.KiosAdapter
import com.setik.pupuk.ui_admin.model.KiosModel
import kotlinx.android.synthetic.main.fragment_kios.*

class KiosFragment : Fragment() {

    private var _binding: FragmentKiosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = KiosAdapter()
    private lateinit var viewModel: KiosModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentKiosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(KiosModel::class.java)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvKios.adapter = adapter

        addKios.setOnClickListener {
            AddKiosFragment().show(childFragmentManager, "")
        }

        viewModel.kios.observe(viewLifecycleOwner, Observer{
            adapter.addKios(it)
        })

        viewModel.getRealtimeUp()
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvKios)

    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            var currentKios = adapter.kioss[position]

            when(direction){
                ItemTouchHelper.RIGHT ->{
                    UpdateKiosFragment(currentKios).show(childFragmentManager,"")
                }
                ItemTouchHelper.LEFT ->{
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Apakah anda yakin?")
                        it.setPositiveButton("Yes"){ dialog, which ->
                            viewModel.deleteKios(currentKios)
                            binding.rvKios.adapter?.notifyItemRemoved(position)
                            Toast.makeText(context,"Pupuk Berhasil dihapus",Toast.LENGTH_SHORT)

                        }
                    }.create().show()
                }
            }
            binding.rvKios.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}