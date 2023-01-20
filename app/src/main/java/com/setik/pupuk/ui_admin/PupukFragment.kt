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
import com.setik.pupuk.databinding.FragmentPupukBinding
import com.setik.pupuk.ui_admin.adapter.PupukAdapter
import com.setik.pupuk.ui_admin.model.PupukModel
import kotlinx.android.synthetic.main.fragment_pupuk.*

class PupukFragment : Fragment() {

    private var _binding: FragmentPupukBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = PupukAdapter()
    private lateinit var viewModel: PupukModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentPupukBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(PupukModel::class.java)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPupuk.adapter = adapter

        addPupuk.setOnClickListener {
            AddPupukFragment().show(childFragmentManager, "")
        }

        viewModel.pupuk.observe(viewLifecycleOwner, Observer{
            adapter.addPupuk(it)
        })

        viewModel.getRealtimeUp()
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvPupuk)

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
            var currentPupuk = adapter.pupuks[position]

            when(direction){
                ItemTouchHelper.RIGHT ->{
                    UpdatePupukFragment(currentPupuk).show(childFragmentManager,"")
                }
                ItemTouchHelper.LEFT ->{
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Apakah anda yakin?")
                        it.setPositiveButton("Yes"){ dialog, which ->
                            viewModel.deletePupuk(currentPupuk)
                            binding.rvPupuk.adapter?.notifyItemRemoved(position)
                            Toast.makeText(context,"Pupuk Berhasil dihapus",Toast.LENGTH_SHORT)

                        }
                    }.create().show()
                }
            }
            binding.rvPupuk.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}