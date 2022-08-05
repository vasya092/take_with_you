package com.example.takewith.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.takewith.BaseApplication
import com.example.takewith.databinding.FragmentAddTakeableSetBinding
import com.example.takewith.model.TakeableSet
import com.example.takewith.ui.viewmodel.AddTakeableSetViewModel
import com.example.takewith.ui.viewmodel.AddTakeableSetViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AddTakeableSetFragment: Fragment() {

    private val navigationArgs: AddTakeableItemFragmentArgs by navArgs()

    private var _binding: FragmentAddTakeableSetBinding? = null
    private val binding get() = _binding!!

    private var takeableSet: TakeableSet? = null

    private val viewModel: AddTakeableSetViewModel by activityViewModels {
        AddTakeableSetViewModelFactory (
            (activity?.application as BaseApplication).database.takeableSetsDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTakeableSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val setId = navigationArgs.setId
        if(setId > 0) {
            viewModel.getTakeableSetById(setId).onEach {
                takeableSet = it
                if (takeableSet != null) {
                    bindTakeableSet(takeableSet!!)
                }
            }.launchIn(lifecycleScope)
        } else {
            binding.saveSetBtn.setOnClickListener{
                addNewTakeableSet()
            }
        }
    }

    private fun addNewTakeableSet() {
        viewModel.addTakeableSet(binding.fieldTitleOfSet.text.toString())
        val listAction = AddTakeableSetFragmentDirections.actionAddTakeableSetFragmentToTakeableSetsListFragment()
        findNavController().navigate(listAction)
    }

    private fun updateTakeableSet() {
        viewModel.updateTakeableSet(
            id = navigationArgs.setId,
            title = binding.fieldTitleOfSet.text.toString()
        )
        val listAction = AddTakeableSetFragmentDirections.actionAddTakeableSetFragmentToTakeableSetsListFragment()
        findNavController().navigate(listAction)
    }

    private fun bindTakeableSet(takeableSet: TakeableSet) {
        binding.apply {
            fieldTitleOfSet.setText(takeableSet.title)
            saveSetBtn.setOnClickListener {
                updateTakeableSet()
            }
        }
    }
}