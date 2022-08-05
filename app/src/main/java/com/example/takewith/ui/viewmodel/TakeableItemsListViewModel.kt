package com.example.takewith.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takewith.data.TakeableDao
import com.example.takewith.data.TakeableSetsDao
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TakeableItemsListViewModel(
    private val takeableDao: TakeableDao,
    private val takeableSetsDao: TakeableSetsDao
): ViewModel() {

    fun getTakeableItemsBySet(setId: Long) : Flow<List<TakeableItem>> {
        return takeableDao.getTakeableItemsBySetId(setId)
    }

    fun deleteTakeableSetById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            takeableSetsDao.deleteTakeableSetById(id)
            takeableDao.deleteTakeableItemsBySetId(id)
        }
    }
}

class TakeableItemsListViewModelFactory(private val takeableDao: TakeableDao, private val takeableSetsDao: TakeableSetsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TakeableItemsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TakeableItemsListViewModel(takeableDao, takeableSetsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
