package com.example.takewith.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takewith.data.TakeableSetsDao
import com.example.takewith.model.TakeableSet
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
}

class AddTakeableSetViewModelFactory(private val takeableSetsDao: TakeableSetsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTakeableSetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTakeableSetViewModel(takeableSetsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
