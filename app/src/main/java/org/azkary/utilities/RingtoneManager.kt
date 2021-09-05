package org.azkary.utilities

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RawRes
import javax.inject.Inject

class RingtoneManager @Inject constructor() {
    private var soundPool: SoundPool? = null
    private var doneSound = 0

    private fun playRingtone(context: Context, @RawRes resId: Int) {
        if (soundPool == null) {
            initSoundPool(context, resId)
            soundPool?.setOnLoadCompleteListener { _, _, _ -> playRingtone(context, resId) }
        }
        soundPool?.play(doneSound, 1f, 1f, 0, 0, 1f)
    }

    fun playDoneRingtoneWithVibration(context: Context, @RawRes resId: Int) {
        playRingtone(context, resId)
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(
                VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            //deprecated in API 26
            v.vibrate(500)
        }
    }

    private fun initSoundPool(context: Context, @RawRes resId: Int) {
        soundPool = createNewSoundPool()
        doneSound = soundPool?.load(context, resId, 1) ?: -1
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