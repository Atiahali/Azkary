package org.azkary.azkardetails.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.azkary.azkardetails.AzkarDetailsViewPagerRepository
import org.azkary.data.model.Zikr
import org.azkary.utilities.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class AzkarDetailsViewPagerViewModel @Inject constructor(
    private val repository: AzkarDetailsViewPagerRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private var mutableStateFlow: MutableStateFlow<List<Zikr>> = MutableStateFlow(emptyList())
    val stateFlow: StateFlow<List<Zikr>> = mutableStateFlow
        .map { azkar ->
            azkar.forEach { zikr ->
                dataStoreManager.setCounter(0, zikr.name)
            }
            azkar
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var _currentItemIndex: MutableLiveData<Int> = MutableLiveData(0)
    val currentItemIndex: LiveData<Int> = _currentItemIndex

    fun setCurrentItem(currentItem: Int) {
        _currentItemIndex.value = currentItem
    }

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

    override fun onCleared() {
        stateFlow.map { azkar ->
            viewModelScope.launch(Dispatchers.IO) {
                azkar.forEach { zikr ->
//                    sharedPreferences.edit {
//                        remove(zikr.name)
//                    }
                }
            }
        }
    }
}

