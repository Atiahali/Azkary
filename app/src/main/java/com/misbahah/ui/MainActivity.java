package com.misbahah.ui;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.misbahah.Utilities.Constants;
import com.misbahah.databinding.ActivityMainBinding;
import com.misbahah.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private MainViewModel mViewModel;

    private TextView counter;
    private TextView topTimes;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        assignVariablestoViews();

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        long topValue = mViewModel.initAndGetPreferences(this).getLong(Constants.TOP_TIMES_OF_ZIKR_KEY, 0);
        topTimes.setText(String.valueOf(topValue));
        setUpProgressBar(topValue);

        mViewModel.getTopTimes().observe(this, topTimes -> this.topTimes.setText(String.valueOf(topTimes)));
        mViewModel.getCurrentTime().observe(this, currentValue -> {
            counter.setText(String.valueOf(currentValue));

            if ((int)(long)currentValue != currentValue) {
                throw new ArithmeticException("integer overflow");
            }
            progressBar.setProgress((int)(long) currentValue);
        });

        binding.getRoot().setOnClickListener((v) -> incrementCounterByOne());
        binding.contentMain.buttonIncr.setOnClickListener((v) -> incrementCounterByOne());
        binding.contentMain.buttonDecr.setOnClickListener((v) -> decrementCounterByOne());
    }

    private void incrementCounterByOne() {
        long incrementedValue = Integer.parseInt(counter.getText().toString()) + 1;
        mViewModel.incrementCounterByOne(getBaseContext(), incrementedValue);
    }

    private void decrementCounterByOne() {
        long decrementedValue = Integer.parseInt(counter.getText().toString()) - 1;
        mViewModel.decrementCounterByOne(decrementedValue);
    }

    private void setUpProgressBar(long topValue) {
        try {
            progressBar.setMax((int) topValue);
            progressBar.setProgress(Integer.parseInt(counter.getText().toString()));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void assignVariablestoViews() {
        counter = binding.contentMain.timer;
        topTimes = binding.contentMain.topTimes;
        progressBar = binding.contentMain.progressBar;
    }
}