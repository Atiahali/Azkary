package com.misbahah.ui.main

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbahah.R
import com.misbahah.service.CheckRecentRun
import com.misbahah.service.CheckRecentRun.Companion.ENABLED
import com.misbahah.utilities.LAST_RUN
import com.misbahah.utilities.MAIN_ACTIVITY_PREFS
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

    private fun initAndGetPreferences(_context: Context): SharedPreferences? {
        val context = _context.applicationContext
        if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(MAIN_ACTIVITY_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences
    }

    fun initializeLastRunPropertyAndRunTheRelatedService(context: Context) {
        initAndGetPreferences(context)
        // First time running app?
        if (!sharedPreferences!!.contains(LAST_RUN)) {
            Log.i("TAG", "YES")
            enableNotification()
        }
        Log.i("MainActivity", "Starting CheckRecentRun service...")
        context.applicationContext.startService(
                Intent(context.applicationContext, CheckRecentRun::class.java)
        )
    }

    private fun recordRunTime() {
        sharedPreferences?.edit {
            putLong(LAST_RUN, System.currentTimeMillis())
        }
    }

    private fun enableNotification() {
        sharedPreferences?.edit {
            putLong(LAST_RUN, System.currentTimeMillis())
            putBoolean(ENABLED, true)
        }
        Log.i("TAG", "Notifications enabled")
    }

    fun disableNotification() {
        sharedPreferences?.edit {
            putLong(LAST_RUN, System.currentTimeMillis())
            putBoolean(ENABLED, false)
        }
        Log.i("TAG", "Notifications disabled")
    }

    private var soundPool: SoundPool? = null
    private var doneSound = 0

    private fun initSoundPool(context: Context) {
        soundPool = createNewSoundPool()
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
        recordRunTime()
        sharedPreferences = null
        soundPool?.release()
        soundPool = null
    }
}