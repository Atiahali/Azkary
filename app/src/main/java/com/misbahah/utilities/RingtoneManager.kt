package com.misbahah.utilities

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.misbahah.R
import javax.inject.Inject

class RingtoneManager @Inject constructor() {
    private var soundPool: SoundPool? = null
    private var doneSound = 0

    fun playDoneRingtone(context: Context) {
        if (soundPool == null) {
            initSoundPool(context)
            soundPool?.setOnLoadCompleteListener { _, _, _ -> playDoneRingtone(context) }
        }
        soundPool?.play(doneSound, 1f, 1f, 0, 0, 1f)
    }

    private fun initSoundPool(context: Context) {
        soundPool = createNewSoundPool()
        doneSound = soundPool?.load(context, R.raw.garas, 1) ?: -1
    }

    private fun createNewSoundPool(): SoundPool {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        return SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()
    }
}