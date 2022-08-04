package com.example.takewith.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.takewith.BaseApplication
import com.example.takewith.databinding.FragmentAddTakeableSetBinding
import com.example.takewith.ui.viewmodel.AddTakeableSetViewModel
import com.example.takewith.ui.viewmodel.AddTakeableSetViewModelFactory

class AddTakeableSetFragment: Fragment() {
    private var _binding: FragmentAddTakeableSetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddTakeableSetViewModel by activityViewModels(){
        AddTakeableSetViewModelFactory (
            (activity?.application as BaseApplication).database.takeableSetsDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTakeableSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveSetBtn.setOnClickListener{
            viewModel.addTakeableSet(binding.fieldTitleOfSet.text.toString())
            val action = AddTakeableSetFragmentDirections.actionAddTakeableSetFragmentToTakeableSetsListFragment()
            findNavController().navigate(action)
        }
    }
}