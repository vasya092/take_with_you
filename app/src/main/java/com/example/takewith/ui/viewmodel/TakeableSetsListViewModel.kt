package com.example.takewith.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.takewith.data.TakeableDao
import com.example.takewith.data.TakeableSetsDao
import com.example.takewith.model.TakeableSet
import kotlinx.coroutines.flow.Flow

class TakeableSetsListViewModel(
    private val takeableSetsDao: TakeableSetsDao
):ViewModel() {
    private val _allTakeableSets: Flow<List<TakeableSet>> = takeableSetsDao.getAllTakeableSets()
    val allTakeableSets: Flow<List<TakeableSet>> = _allTakeableSets
}

class TakeableSetsListViewModelFactory(private val takeableSetsDao: TakeableSetsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TakeableSetsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TakeableSetsListViewModel(takeableSetsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
