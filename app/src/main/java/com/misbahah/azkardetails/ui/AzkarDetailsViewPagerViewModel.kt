package com.misbahah.azkardetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misbahah.azkardetails.AzkarDetailsViewPagerRepository
import com.misbahah.data.model.Zikr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarDetailsViewPagerViewModel @Inject constructor(
    private val repository: AzkarDetailsViewPagerRepository
) : ViewModel() {
    private var mutableStateFlow: MutableStateFlow<List<Zikr>?> = MutableStateFlow(null)

    val stateFlow: StateFlow<List<Zikr>?> = mutableStateFlow

    fun getAzkarByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            mutableStateFlow.value = repository.getAzkarByCategoryId(categoryId).azkar
        }
    }

    fun getVariousAzkar() {
        viewModelScope.launch {
            mutableStateFlow.value = repository.getVariousAzkar()
        }
    }
}

