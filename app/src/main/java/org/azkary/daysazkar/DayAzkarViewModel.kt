package org.azkary.daysazkar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private var _categoryList = mutableStateOf<List<Category>>(emptyList())
    val categoryList: State<List<Category>> = _categoryList

    fun getAllCategories() {
        viewModelScope.launch {
            val deferred = async {
                dayAzkarRepository.getAllCategories()
            }

            _categoryList.value = deferred.await()
        }

    }

}