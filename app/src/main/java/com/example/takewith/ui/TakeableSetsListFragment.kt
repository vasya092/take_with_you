package com.example.takewith.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.takewith.BaseApplication
import com.example.takewith.databinding.FragmentTakeableSetsListBinding
import com.example.takewith.ui.adapter.TakeableSetsListAdapter
import com.example.takewith.ui.viewmodel.TakeableSetsListViewModel
import com.example.takewith.ui.viewmodel.TakeableSetsListViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TakeableSetsListFragment: Fragment() {
    private var _binding: FragmentTakeableSetsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TakeableSetsListViewModel by activityViewModels() {
        TakeableSetsListViewModelFactory(
            (activity?.application as BaseApplication).database.takeableSetsDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTakeableSetsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TakeableSetsListAdapter {
            val action = TakeableSetsListFragmentDirections.actionTakeableSetsListFragmentToTakeableItemsListFragment()
            findNavController().navigate(action)
        }

        viewModel.allTakeableSets.onEach { takeableSets ->
            takeableSets.let {
                adapter.submitList(it)
            }
        }
        .launchIn(lifecycleScope)
        binding.setsList.adapter = adapter

    }
}