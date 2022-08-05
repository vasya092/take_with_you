package com.example.takewith.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.takewith.data.TakeableDao
import com.example.takewith.model.TakeableItem
import kotlinx.coroutines.flow.Flow

class DetailTakeableItemViewModel(
    private val takeableDao: TakeableDao
):ViewModel() {
    fun getTakeableItem(id: Long) : Flow<TakeableItem> {
        return takeableDao.getTakeableItem(id)
    }
}

class DetailTakeableItemViewModelFactory(private val takeableDao: TakeableDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailTakeableItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailTakeableItemViewModel(takeableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
