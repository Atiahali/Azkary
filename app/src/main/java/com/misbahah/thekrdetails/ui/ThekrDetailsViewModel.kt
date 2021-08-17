package com.misbahah.thekrdetails.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbahah.utilities.RingtoneManager
import com.misbahah.utilities.TOP_TIMES_OF_ZIKR_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThekrDetailsViewModel  @Inject constructor(
    private val ringtoneManager: RingtoneManager,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _topTimes = MutableLiveData<Long>(getTopValue())
    private val _currentTime = MutableLiveData<Long>()

    val topTimes: LiveData<Long> = _topTimes
    val currentTime: LiveData<Long> = _currentTime

    fun incrementCounterByOne(context: Context, incrementedValue: Long) {
        _currentTime.value = incrementedValue
        updateTopTimes(context, incrementedValue)
    }

    fun decrementCounterByOne(decrementedValue: Long) {
        if (decrementedValue >= 0) _currentTime.value = decrementedValue
    }

    private fun updateTopTimes(context: Context, currentValue: Long) {
        if (currentValue % 100 == 0L) {
            ringtoneManager.playDoneRingtone(context)
        }

        val topTimes = getTopValue()
        if (currentValue > topTimes) {
            this._topTimes.value = currentValue
            sharedPreferences.edit()
                .putLong(TOP_TIMES_OF_ZIKR_KEY, currentValue)
                .apply()
        }
    }

    fun getTopValue(): Long = sharedPreferences.getLong(TOP_TIMES_OF_ZIKR_KEY, 0)
}