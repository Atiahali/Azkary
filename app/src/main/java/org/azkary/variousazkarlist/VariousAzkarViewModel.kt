package org.azkary.variousazkarlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.azkary.data.model.Zikr
import javax.inject.Inject

@HiltViewModel
class VariousAzkarViewModel @Inject constructor(
    private val repository: VariousAzkarRepository
) : ViewModel() {
    private val _variousAzkarStateFlow = mutableStateOf<List<Zikr>>(emptyList())

    val variousAzkarStateFlow: State<List<Zikr>> = _variousAzkarStateFlow

    fun getVariousAzkar() =
        viewModelScope.launch {
            _variousAzkarStateFlow.value = repository.getVariousAzkar()
        }
}