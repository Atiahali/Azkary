package com.misbahah.zikrdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.misbahah.R
import com.misbahah.databinding.ThekrDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZikrDetailsFragment : Fragment() {

    private val mViewModel by viewModels<ZikrDetailsViewModel>()

    private lateinit var binding: ThekrDetailsFragmentBinding

    private lateinit var counterTextView: TextView
    private lateinit var topTimesTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.thekr_details_fragment, container, false)

        initViews()

        setUpProgressBar(mViewModel.getTopValue())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewModel.currentTime.observe(viewLifecycleOwner, { currentValue: Long ->
            counterTextView.text = currentValue.toString()
            progressBar.progress = currentValue.toBigInteger().toInt()
        })


    }

    private fun initViews() {
        binding.thekrDetailsFragment = this
        binding.model = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.currentTime = mViewModel.currentTime.value?.toInt() ?: -1

        counterTextView = binding.timer
        topTimesTextView = binding.topTimes
        progressBar = binding.progressBar
    }

    private fun setUpProgressBar(topValue: Long) {
        try {
            progressBar.max = topValue.toInt()
            progressBar.progress = counterTextView.text.toString().toInt()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun incrementCounterByOne() {
        val incrementedValue = (counterTextView.text.toString().toInt() + 1).toLong()

        mViewModel.incrementCounterByOne(requireContext(), incrementedValue)
    }

    fun decrementCounterByOne() {
        val decrementedValue = (counterTextView.text.toString().toInt() - 1).toLong()
        mViewModel.decrementCounterByOne(decrementedValue)
    }
}