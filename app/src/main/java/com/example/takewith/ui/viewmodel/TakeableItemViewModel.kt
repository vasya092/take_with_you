package com.example.takewith.ui.viewmodel

import androidx.lifecycle.*
import com.example.takewith.data.TakeableDao
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TakeableItemViewModel(
    private val takeableDao: TakeableDao
): ViewModel() {

    val allTakeableItems:LiveData<List<TakeableItem>> = takeableDao.getAllTakeableItems().asLiveData()

    fun getTakeableItem(id: Long) : LiveData<TakeableItem> {
        return takeableDao.getTakeableItem(id).asLiveData()
    }

    fun addTakeableItem(
        title: String,
        count: Int
    ) {
        val takeableItem = TakeableItem(
            title = title,
            count = 2
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
