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
import com.example.takewith.databinding.FragmentTakeableItemDetailBinding
import com.example.takewith.model.TakeableItem
import com.example.takewith.ui.viewmodel.TakeableItemViewModel
import com.example.takewith.ui.viewmodel.TakeableItemViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TakeableItemDetailFragment : Fragment() {

    private val navigationArgs: TakeableItemDetailFragmentArgs by navArgs()

    private val viewModel: TakeableItemViewModel by activityViewModels() {
        TakeableItemViewModelFactory(
            (activity?.application as BaseApplication).database.takeableDao()
        )
    }

    private lateinit var takeableItem: TakeableItem

    private var _binding: FragmentTakeableItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTakeableItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id

        viewModel.getTakeableItem(id).onEach {
            takeableItem = it
            bindTakeableItem()
        }.launchIn(lifecycleScope)
    }

    private fun bindTakeableItem() {
        binding.apply {
            name.text = takeableItem.title
            count.text = takeableItem.count.toString() + " шт."
            editTakeableItemFab.setOnClickListener {
                val action = TakeableItemDetailFragmentDirections
                    .actionTakeableItemDetailFragmentToAddTakeableItemFragment(takeableItem.id)
                findNavController().navigate(action)
            }
        }
    }
}
