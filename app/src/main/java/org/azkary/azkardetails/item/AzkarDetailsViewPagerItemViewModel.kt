package org.azkary.azkardetails.item

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.azkary.R
import org.azkary.data.model.Zikr
import org.azkary.di.IODispatcher
import org.azkary.utilities.DataStoreManager
import org.azkary.utilities.RingtoneManager
import javax.inject.Inject

@HiltViewModel
class AzkarDetailsViewPagerItemViewModel @Inject constructor(
    private val ringtoneManager: RingtoneManager,
    private val dataStoreManager: DataStoreManager,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var zikr: Zikr? = null

    private var _currentTime = MutableLiveData<Int>()

    val currentTime: LiveData<Int> = _currentTime

    fun setZikr(zikr: Zikr) {
        this.zikr = zikr
        viewModelScope.launch {
            dataStoreManager.getCounger(zikr.name).collect {
                _currentTime.value = it
            }
        }
    }

    fun incrementCounterByOne(incrementedValue: Int) {
        viewModelScope.launch(ioDispatcher) {
            dataStoreManager.setCounter(incrementedValue, zikr!!.name)
        }
    }

    fun setCurrentTimeByZikrName(currentTime: Int) {
        viewModelScope.launch(ioDispatcher) {
            dataStoreManager.setCounter(currentTime, zikr!!.name)
        }
    }

    fun playDoneRingtoneWithVibration(context: Context) {
        ringtoneManager.playDoneRingtoneWithVibration(context, R.raw.garas)
    }

}