package com.example.takewith.ui.viewmodel

import androidx.lifecycle.*
import com.example.takewith.data.TakeableDao
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class AddTakeableItemViewModel(
    private val takeableDao: TakeableDao
): ViewModel() {

    fun getTakeableItem(id: Long) : Flow<TakeableItem> {
        return takeableDao.getTakeableItem(id)
    }

    fun addTakeableItem(
        title: String,
        count: Int,
        setId: Long
    ) {
        val takeableItem = TakeableItem(
            title = title,
            count = count,
            setId = setId
        )

        viewModelScope.launch {
            takeableDao.insert(takeableItem)
        }
    }

    fun updateTakeableItem(
        id: Long,
        title: String,
        count: Int,
        setId: Long
    ) {
        val takeableItem = TakeableItem(
            id = id,
            title = title,
            count = count,
            setId = setId
        )
        viewModelScope.launch(Dispatchers.IO) {
            takeableDao.update(takeableItem)
        }
    }

    fun deleteTakeableItem(takeableItem: TakeableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            takeableDao.delete(takeableItem)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

class AddTakeableItemViewModelFactory(private val takeableDao: TakeableDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTakeableItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTakeableItemViewModel(takeableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
