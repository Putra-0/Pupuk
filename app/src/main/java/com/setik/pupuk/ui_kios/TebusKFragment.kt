package com.setik.pupuk.ui_kios

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.setik.pupuk.databinding.FragmentTebusKBinding
import com.setik.pupuk.ui_admin.AddTebusFragment
import com.setik.pupuk.ui_kios.adapter.TebusKAdapter
import com.setik.pupuk.ui_kios.model.TebusKModel
import kotlinx.android.synthetic.main.fragment_tebus_k.*

class TebusKFragment : Fragment() {

    private var _binding: FragmentTebusKBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = TebusKAdapter()
    private lateinit var viewModel: TebusKModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTebusKBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(TebusKModel::class.java)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTebusk.adapter = adapter

        addTebus.setOnClickListener {
            AddTebusFragment().show(childFragmentManager, "")
        }
        viewModel.tebus.observe(viewLifecycleOwner, Observer{
            adapter.addTebus(it)
        })
        viewModel.getRealtimeUp()
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTebusk)

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
            val position = viewHolder.adapterPosition
            val currentTebus = adapter.tebuss[position]

            when(direction){
                ItemTouchHelper.RIGHT ->{
                    UpdateTebusFragment(currentTebus).show(childFragmentManager, "")
                }
                ItemTouchHelper.LEFT ->{
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Apakah anda yakin?")
                        it.setPositiveButton("Yes"){ dialog, which ->
                            viewModel.deleteTebus(currentTebus)
                            binding.rvTebusk.adapter?.notifyItemRemoved(position)
                            Toast.makeText(context,"Penebusan Berhasil dihapus", Toast.LENGTH_SHORT)
                        }
                    }.create().show()
                }
            }
            binding.rvTebusk.adapter?.notifyItemChanged(position)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}