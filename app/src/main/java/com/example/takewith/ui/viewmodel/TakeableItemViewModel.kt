package com.example.takewith.ui.viewmodel

import androidx.lifecycle.*
import com.example.takewith.data.TakeableDao
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class TakeableItemViewModel(
    private val takeableDao: TakeableDao
): ViewModel() {

    private val _allTakeableItems: Flow<List<TakeableItem>> = takeableDao.getAllTakeableItems()
    val allTakeableItems: Flow<List<TakeableItem>> = _allTakeableItems

    fun getTakeableItem(id: Long) : Flow<TakeableItem> {
        return takeableDao.getTakeableItem(id)
    }

    fun addTakeableItem(
        title: String,
        count: Int
    ) {
        val takeableItem = TakeableItem(
            title = title,
            count = count
        )

        viewModelScope.launch {
            takeableDao.insert(takeableItem)
        }
    }

    fun updateTakeableItem(
        id: Long,
        title: String,
        count: Int
    ) {
        val takeableItem = TakeableItem(
            id = id,
            title = title,
            count = count
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

class TakeableItemViewModelFactory(private val takeableDao: TakeableDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TakeableItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TakeableItemViewModel(takeableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
