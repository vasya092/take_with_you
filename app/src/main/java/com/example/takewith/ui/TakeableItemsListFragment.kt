package com.example.takewith.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.takewith.BaseApplication
import com.example.takewith.R
import com.example.takewith.databinding.FragmentTakeableItemsListBinding
import com.example.takewith.ui.adapter.TakeableItemsListAdapter
import com.example.takewith.ui.viewmodel.TakeableItemViewModel
import com.example.takewith.ui.viewmodel.TakeableItemViewModelFactory

class TakeableItemsListFragment : Fragment() {

    private val viewModel: TakeableItemViewModel by activityViewModels() {
        TakeableItemViewModelFactory(
            (activity?.application as BaseApplication).database.takeableDao()
        )
    }

    private var _binding: FragmentTakeableItemsListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTakeableItemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TakeableItemsListAdapter { takeableItem ->
            val action = TakeableItemsListFragmentDirections
                .actionTakeableItemsListFragmentToTakeableItemDetailFragment(takeableItem.id)
            findNavController().navigate(action)
        }
        viewModel.allTakeableItems.observe(this.viewLifecycleOwner) { takeableItems ->
            takeableItems.let {
                adapter.submitList(it)
            }
        }

        binding.apply {
            recyclerView.adapter = adapter
            addTakeableItemFab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_takebleItemsListFragment_to_addTakeableItemFragment
                )
            }
        }
    }
}
