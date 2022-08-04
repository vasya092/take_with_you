package com.example.takewith.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.takewith.BaseApplication
import com.example.takewith.R
import com.example.takewith.databinding.FragmentTakeableItemsListBinding
import com.example.takewith.model.TakeableItem
import com.example.takewith.model.TakeableSet
import com.example.takewith.ui.adapter.TakeableItemsListAdapter
import com.example.takewith.ui.viewmodel.TakeableItemsListViewModel
import com.example.takewith.ui.viewmodel.TakeableItemsListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TakeableItemsListFragment : Fragment() {

    private val viewModel: TakeableItemsListViewModel by activityViewModels {
        TakeableItemsListViewModelFactory(
            (activity?.application as BaseApplication).database.takeableDao(),
            (activity?.application as BaseApplication).database.takeableSetsDao()
        )
    }

    private var _binding: FragmentTakeableItemsListBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: TakeableItemsListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTakeableItemsListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TakeableItemsListAdapter { takeableItem ->
            val detailAction = TakeableItemsListFragmentDirections
                .actionTakeableItemsListFragmentToTakeableItemDetailFragment(takeableItem.id, takeableItem.setId)
            findNavController().navigate(detailAction)
        }

        viewModel.getTakeableItemsBySet(navigationArgs.setId)
            .onEach { takeableItems ->
                takeableItems.let {
                    adapter.submitList(it)
                }
            }
            .launchIn(lifecycleScope)

        val addAction =
            TakeableItemsListFragmentDirections.actionTakebleItemsListFragmentToAddTakeableItemFragment(
                setId = navigationArgs.setId
            )
        binding.apply {
            recyclerView.adapter = adapter
            addTakeableItemFab.setOnClickListener {
                findNavController().navigate(addAction)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.setGroupVisible(R.id.group_edit_list, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            deleteTakeableSetById(navigationArgs.setId)
        }
        else if(item.itemId == R.id.action_edit) {
            val actionEdit = TakeableItemsListFragmentDirections.actionTakeableItemsListFragmentToAddTakeableSetFragment(navigationArgs.setId)
            findNavController().navigate(actionEdit)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun deleteTakeableSetById(id: Long) {
        val listAction =
            TakeableItemsListFragmentDirections.actionTakeableItemsListFragmentToTakeableSetsListFragment()
        viewModel.deleteTakeableSetById(id)
        findNavController().navigate(listAction)
    }

}