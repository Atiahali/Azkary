package com.misbahah.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.misbahah.Utilities.Constants;
import com.misbahah.databinding.ActivityMainBinding;
import com.misbahah.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private MainViewModel mViewModel;

    private TextView timer;
    private TextView topTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        assignVariablestoViews();

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        long topValue = mViewModel.initAndGetPreferences(this).getLong(Constants.TOP_TIMES_OF_ZIKR_KEY, 0);
        topTimes.setText(String.valueOf(topValue));

        mViewModel.getTopTimes().observe(this, topTimes -> this.topTimes.setText(String.valueOf(topTimes)));
        mViewModel.getCurrentTime().observe(this, currentValue -> timer.setText(String.valueOf(currentValue)));

        binding.getRoot().setOnClickListener((v) -> {
            long newValue = Integer.parseInt(timer.getText().toString()) + 1;
            mViewModel.incrementCounterByOne(getBaseContext(), newValue);
        });
    }

    private void assignVariablestoViews() {
        timer = binding.contentMain.timer;
        topTimes = binding.contentMain.topTimes;
    }
}