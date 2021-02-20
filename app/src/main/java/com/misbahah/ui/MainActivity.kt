package com.misbahah.ui

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.misbahah.Utilities.Constants
import com.misbahah.databinding.ActivityMainBinding
import com.misbahah.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mViewModel: MainViewModel? = null

    private lateinit var counter: TextView
    private lateinit var topTimes: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        assignVariablesViews()

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val topValue = mViewModel?.initAndGetPreferences(this)
                ?.getLong(Constants.TOP_TIMES_OF_ZIKR_KEY, 0) ?: 0
        topTimes.text = topValue.toString()
        setUpProgressBar(topValue)

        mViewModel?.topTimes?.observe(this, { topTimes: Long ->
            this.topTimes.text = topTimes.toString()
        })
        mViewModel?.currentTime?.observe(this, { currentValue: Long ->
            counter.text = currentValue.toString()
            progressBar.progress = currentValue.toBigInteger().toInt()
        })

        binding.root.setOnClickListener { incrementCounterByOne() }
        binding.contentMain.buttonIncr.setOnClickListener { incrementCounterByOne() }
        binding.contentMain.buttonDecr.setOnClickListener { decrementCounterByOne() }
    }

    private fun incrementCounterByOne() {
        val incrementedValue = (counter.text.toString().toInt() + 1).toLong()
        mViewModel?.incrementCounterByOne(baseContext, incrementedValue)
    }

    private fun decrementCounterByOne() {
        val decrementedValue = (counter.text.toString().toInt() - 1).toLong()
        mViewModel?.decrementCounterByOne(decrementedValue)
    }

    private fun setUpProgressBar(topValue: Long) {
        try {
            progressBar.max = topValue.toInt()
            progressBar.progress = counter.text.toString().toInt()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun assignVariablesViews() {
        counter = binding.contentMain.timer
        topTimes = binding.contentMain.topTimes
        progressBar = binding.contentMain.progressBar
    }
}