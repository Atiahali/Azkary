package com.misbahah.ui;

import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.misbahah.R;
import com.misbahah.Utilities.Constants;
import com.misbahah.databinding.ActivityMainBinding;
import com.misbahah.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        long topValue = mViewModel.initAndGetPreferences(this).getLong(Constants.TOP_TIMES_OF_ZIKR_KEY, 0);
        binding.contentMain.topTimes.setText(String.valueOf(topValue));

        mViewModel.getTopTimes().observe(this, topTimes -> binding.contentMain.topTimes.setText(String.valueOf(topTimes)));
        mViewModel.getCurrentTime().observe(this, currenValue -> binding.timer.setText(String.valueOf(currenValue)));

        binding.getRoot().setOnClickListener((v) -> {

            long newValue = Integer.parseInt(binding.timer.getText().toString()) + 1;
            mViewModel.getCurrentTime().setValue(newValue);

            mViewModel.updateTopTimes(
                    getBaseContext(),
                    Long.parseLong(binding.timer.getText().toString())
            );
        });
    }
}