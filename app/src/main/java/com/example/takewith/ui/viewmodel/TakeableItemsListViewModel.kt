package com.example.takewith.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.takewith.data.TakeableDao
import com.example.takewith.data.TakeableSetsDao
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.flow.Flow

class TakeableItemsListViewModel(
    private val takeableDao: TakeableDao
): ViewModel() {

    fun getTakeableItemsBySet(setId: Long) : Flow<List<TakeableItem>> {
        return takeableDao.getTakeableItemsBySetId(setId)
    }
}

class TakeableItemsListViewModelFactory(private val takeableDao: TakeableDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TakeableItemsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TakeableItemsListViewModel(takeableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
