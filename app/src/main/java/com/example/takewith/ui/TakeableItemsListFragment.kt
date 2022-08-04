package com.example.takewith.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.takewith.BaseApplication
import com.example.takewith.R
import com.example.takewith.databinding.FragmentTakeableItemsListBinding
import com.example.takewith.ui.adapter.TakeableItemsListAdapter
import com.example.takewith.ui.viewmodel.TakeableItemViewModel
import com.example.takewith.ui.viewmodel.TakeableItemViewModelFactory
import com.example.takewith.ui.viewmodel.TakeableItemsListViewModel
import com.example.takewith.ui.viewmodel.TakeableItemsListViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TakeableItemsListFragment : Fragment() {

    private val viewModel: TakeableItemsListViewModel by activityViewModels {
        TakeableItemsListViewModelFactory(
            (activity?.application as BaseApplication).database.takeableDao()
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
        Toast.makeText(context, "Create fragment toast", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit) {
            //findNavController().navigate()
        }
        return super.onOptionsItemSelected(item)
    }
}
