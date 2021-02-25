package com.misbahah.ui.main

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbahah.R
import com.misbahah.utilities.TOP_TIMES_OF_ZIKR_KEY

class MainViewModel : ViewModel() {

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
            playDoneRingtone(context)
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

    private fun initAndGetPreferences(context: Context): SharedPreferences? {
        if (sharedPreferences == null) sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences
    }

    private var soundPool: SoundPool? = null
    private var doneSound = 0

    private fun initSoundPool(context: Context) {
        soundPool = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            createNewSoundPool()
        else
            SoundPool(1, AudioManager.STREAM_MUSIC, 0))

        doneSound = soundPool?.load(context, R.raw.notification, 1) ?: -1
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNewSoundPool(): SoundPool {
        val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        return SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build()
    }

    private fun playDoneRingtone(context: Context) {
        if (soundPool == null) {
            initSoundPool(context)
            soundPool?.setOnLoadCompleteListener { _, _, _ -> playDoneRingtone(context) }
        }
        soundPool?.play(doneSound, 1f, 1f, 0, 0, 1f)
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences = null
        soundPool?.release()
        soundPool = null
    }
}