package org.azkary.daysazkar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.azkary.data.model.Category
import javax.inject.Inject

@HiltViewModel
class DayAzkarViewModel @Inject constructor(
    private val dayAzkarRepository: DayAzkarRepository
) : ViewModel() {
    private var _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> = _categoryList

    fun getAllCategories() {
        viewModelScope.launch {
            val deferred = async {
                dayAzkarRepository.getAllCategories()
            }

            _categoryList.value = deferred.await()
        }

    }

}