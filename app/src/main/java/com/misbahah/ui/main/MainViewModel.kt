package com.misbahah.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbahah.utilities.MAIN_ACTIVITY_PREFS
import com.misbahah.utilities.RingtoneManager
import com.misbahah.utilities.TOP_TIMES_OF_ZIKR_KEY

class MainViewModel : ViewModel() {

    private val ringtoneManager: RingtoneManager by lazy {
        RingtoneManager()
    }
    val topTimes = MutableLiveData<Long>(0)
    val currentTime = MutableLiveData<Long>()
    private var sharedPreferences: SharedPreferences? = null

    fun incrementCounterByOne(context: Context, incrementedValue: Long) {
        currentTime.value = incrementedValue
        updateTopTimes(context, incrementedValue)
    }

    fun decrementCounterByOne(decrementedValue: Long) {
        if (decrementedValue >= 0) currentTime.value = decrementedValue
    }

    private fun updateTopTimes(context: Context, currentValue: Long) {
        if (currentValue % 100 == 0L) {
            ringtoneManager.playDoneRingtone(context)
        }
        initAndGetPreferences(context)

        val topTimes = sharedPreferences!!.getLong(TOP_TIMES_OF_ZIKR_KEY, 0)
        if (currentValue > topTimes) {
            this.topTimes.value = currentValue
            sharedPreferences!!.edit()
                .putLong(TOP_TIMES_OF_ZIKR_KEY, currentValue)
                .apply()
        }
    }

    fun getTopValue(context: Context): Long = this.initAndGetPreferences(context)
        ?.getLong(TOP_TIMES_OF_ZIKR_KEY, 0) ?: 0

    private fun initAndGetPreferences(_context: Context): SharedPreferences? {
        val context = _context.applicationContext
        if (sharedPreferences == null) sharedPreferences =
            context.getSharedPreferences(MAIN_ACTIVITY_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences
    }


    override fun onCleared() {
        super.onCleared()
        sharedPreferences = null
        ringtoneManager.release()
    }

}