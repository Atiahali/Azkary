package com.misbahah.ui.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.misbahah.R;
import com.misbahah.Utilities.Constants;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Long> topTimes = new MutableLiveData<>();
    private final MutableLiveData<Long> currentTime = new MutableLiveData<>();
    private SharedPreferences sharedPreferences;

    public void incrementCounterByOne(Context context, long newValue) {
        getCurrentTime().setValue(newValue);

        updateTopTimes(context, newValue);
    }

    public void updateTopTimes(@NonNull Context context, long currentValue) {
        if (currentValue % 100 == 0) {
            playDoneRingtone(context);
        }
        initAndGetPreferences(context);

        long topTimes = sharedPreferences.getLong(Constants.TOP_TIMES_OF_ZIKR_KEY, 0);
        if (currentValue > topTimes) {
            this.topTimes.setValue(currentValue);
            sharedPreferences.edit()
                    .putLong(Constants.TOP_TIMES_OF_ZIKR_KEY, currentValue)
                    .apply();
        }
    }

    private SoundPool soundPool;
    private int doneSound;

    private void playDoneRingtone(Context context) {
        if (soundPool == null) {
            initSoundPool(context);
            soundPool.setOnLoadCompleteListener(
                    (soundPool, sampleId, status) -> playDoneRingtone(context)
            );
        }
        soundPool.play(doneSound, 1, 1, 0, 0, 1);
    }

    private void initSoundPool(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = createNewSoundPool();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        doneSound = soundPool.load(context, R.raw.notification, 1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    public SharedPreferences initAndGetPreferences(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences;
    }

    public MutableLiveData<Long> getTopTimes() {
        return topTimes;
    }

    public MutableLiveData<Long> getCurrentTime() {
        return currentTime;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        sharedPreferences = null;
        soundPool.release();
        soundPool = null;
    }
}
