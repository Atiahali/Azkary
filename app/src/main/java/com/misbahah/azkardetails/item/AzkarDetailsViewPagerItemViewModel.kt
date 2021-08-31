package com.misbahah.azkardetails.item

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misbahah.R
import com.misbahah.data.model.Zikr
import com.misbahah.di.IODispatcher
import com.misbahah.utilities.RingtoneManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarDetailsViewPagerItemViewModel @Inject constructor(
    private val ringtoneManager: RingtoneManager,
    private val sharedPreferences: SharedPreferences,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _currentTime = MutableLiveData<Int>()

    val currentTime: LiveData<Int> = _currentTime

    fun incrementCounterByOne(incrementedValue: Int) {
        _currentTime.value = incrementedValue
    }

    fun setCurrentTimeByZikrName(currentTime: Int, zikr: Zikr) {
        viewModelScope.launch(ioDispatcher) {
            sharedPreferences.edit {
                putInt(zikr.name, currentTime)
                _currentTime.postValue(currentTime)
            }
        }
    }

    fun getCurrentTimeByZikrName(zikr: Zikr) {
        _currentTime.value = sharedPreferences.getInt(zikr.name, 0)
    }

    fun playDoneRingtoneWithVibration(context: Context) {
        ringtoneManager.playDoneRingtoneWithVibration(context, R.raw.garas)
    }

}