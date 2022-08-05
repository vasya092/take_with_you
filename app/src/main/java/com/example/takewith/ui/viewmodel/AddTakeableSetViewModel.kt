package com.example.takewith.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takewith.data.TakeableSetsDao
import com.example.takewith.model.TakeableSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddTakeableSetViewModel(
    private val takeableSetsDao: TakeableSetsDao
): ViewModel() {

    fun addTakeableSet(title: String) {
        val takeableSet = TakeableSet(title = title)
        viewModelScope.launch {
            takeableSetsDao.insert(takeableSet)
        }
    }

    fun getTakeableSetById(id: Long) : Flow<TakeableSet> {
        return takeableSetsDao.getTakeableSetById(id)
    }

    fun updateTakeableSet(id: Long, title: String) {
        val takeableSet = TakeableSet(id, title)
        viewModelScope.launch(Dispatchers.IO) {
            takeableSetsDao.update(takeableSet)
        }
    }
}

class AddTakeableSetViewModelFactory(private val takeableSetsDao: TakeableSetsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTakeableSetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTakeableSetViewModel(takeableSetsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
