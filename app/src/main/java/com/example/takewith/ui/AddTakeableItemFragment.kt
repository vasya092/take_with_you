package com.example.takewith.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.takewith.BaseApplication
import com.example.takewith.R
import com.example.takewith.databinding.FragmentAddTakeableItemBinding
import com.example.takewith.model.TakeableItem
import com.example.takewith.ui.viewmodel.TakeableItemViewModel
import com.example.takewith.ui.viewmodel.TakeableItemViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AddTakeableItemFragment : Fragment() {

    private val navigationArgs: AddTakeableItemFragmentArgs by navArgs()

    private var _binding: FragmentAddTakeableItemBinding? = null

    private lateinit var takeableItem: TakeableItem
    lateinit var navToList: NavDirections

    private val binding get() = _binding!!

    private val viewModel: TakeableItemViewModel by activityViewModels() {
        TakeableItemViewModelFactory(
            (activity?.application as BaseApplication).database.takeableDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTakeableItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        navToList = AddTakeableItemFragmentDirections.actionAddTakeableItemFragmentToTakeablesItemsListFragment(setId = navigationArgs.setId)

        if (id > 0) {
            viewModel.getTakeableItem(id).onEach {
                takeableItem = it
                bindTakeableItem(takeableItem)
            }.launchIn(lifecycleScope)

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteTakeableItem(takeableItem)
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addTakeableItem()
            }
        }
    }



    private fun deleteTakeableItem(takeableItem: TakeableItem) {
        viewModel.deleteTakeableItem(takeableItem)
        findNavController().navigate(navToList)
    }

    private fun addTakeableItem() {
        if (isValidEntry()) {
            viewModel.addTakeableItem(
                binding.nameInput.text.toString(),
                Integer.parseInt(binding.takeableItemCountInput.text.toString()),
                navigationArgs.setId
            )
            findNavController().navigate(navToList)
        }
    }

    private fun updateTakeableItem() {
        if (isValidEntry()) {
            viewModel.updateTakeableItem(
                id = navigationArgs.id,
                title = binding.nameInput.text.toString(),
                count = Integer.parseInt(binding.takeableItemCountInput.text.toString())
            )
            findNavController().navigate(navToList)
        }
    }

    private fun bindTakeableItem(takeableItem: TakeableItem) {
        binding.apply{
            nameInput.setText(takeableItem.title, TextView.BufferType.SPANNABLE)
            takeableItemCountInput.setText(takeableItem.count.toString(), TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                updateTakeableItem()
            }
        }

    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.takeableItemCountInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
