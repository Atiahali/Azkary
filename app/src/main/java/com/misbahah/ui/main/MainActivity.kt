package com.misbahah.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.misbahah.R
import com.misbahah.broadcast.MyReceiver
import com.misbahah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var counterTextView: TextView
    private lateinit var topTimesTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeViewsAndBindingVariables()
        
        val topValue = mViewModel.getTopValue(this)

        setUpProgressBar(topValue)

        mViewModel.currentTime.observe(this, { currentValue: Long ->
            counterTextView.text = currentValue.toString()
            progressBar.progress = currentValue.toBigInteger().toInt()
        })

        mViewModel.initializeLastRunProperty()
        mViewModel.recordRunTime()
        Log.i("MainActivity", "Starting MyReceiver broadcast...")
        sendBroadcast(Intent(this, MyReceiver::class.java))
    }

    fun incrementCounterByOne() {
        val incrementedValue = (counterTextView.text.toString().toInt() + 1).toLong()
        mViewModel.incrementCounterByOne(baseContext, incrementedValue)
    }

    fun decrementCounterByOne() {
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

    private fun initializeViewsAndBindingVariables() {
        binding.mainActivity = this
        binding.model = mViewModel
        binding.lifecycleOwner = this
        binding.currentTime = mViewModel.currentTime.value?.toInt() ?: 0

        counterTextView = binding.timer
        topTimesTextView = binding.topTimes
        progressBar = binding.progressBar
    }
}