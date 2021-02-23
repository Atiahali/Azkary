package com.misbahah.ui

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.misbahah.utilities.TOP_TIMES_OF_ZIKR_KEY
import com.misbahah.databinding.ActivityMainBinding
import com.misbahah.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()

    private lateinit var counterTextView: TextView
    private lateinit var topTimesTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        assignVariablesViews()

        val topValue = mViewModel.initAndGetPreferences(this)
                ?.getLong(TOP_TIMES_OF_ZIKR_KEY, 0) ?: 0

        topTimesTextView.text = topValue.toString()
        setUpProgressBar(topValue)

        mViewModel.topTimes.observe(this, { topTimes: Long ->
            this.topTimesTextView.text = topTimes.toString()
        })
        mViewModel.currentTime.observe(this, { currentValue: Long ->
            counterTextView.text = currentValue.toString()
            progressBar.progress = currentValue.toBigInteger().toInt()
        })

        binding.root.setOnClickListener { incrementCounterByOne() }
        binding.contentMain.buttonIncr.setOnClickListener { incrementCounterByOne() }
        binding.contentMain.buttonDecr.setOnClickListener { decrementCounterByOne() }
    }

    private fun incrementCounterByOne() {
        val incrementedValue = (counterTextView.text.toString().toInt() + 1).toLong()
        mViewModel.incrementCounterByOne(baseContext, incrementedValue)
    }

    private fun decrementCounterByOne() {
        val decrementedValue = (counterTextView.text.toString().toInt() - 1).toLong()
        mViewModel.decrementCounterByOne(decrementedValue)
    }

    private fun setUpProgressBar(topValue: Long) {
        try {
            progressBar.max = topValue.toInt()
            progressBar.progress = counterTextView.text.toString().toInt()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun assignVariablesViews() {
        counterTextView = binding.contentMain.timer
        topTimesTextView = binding.contentMain.topTimes
        progressBar = binding.contentMain.progressBar
    }
}