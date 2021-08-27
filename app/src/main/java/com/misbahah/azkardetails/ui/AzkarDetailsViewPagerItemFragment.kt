package com.misbahah.azkardetails.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misbahah.R
import com.misbahah.categories.ui.CategoriesFragmentDirections
import com.misbahah.data.model.Zikr
import com.misbahah.databinding.FragmentAzkarDetailsViewPagerItemBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_ZIKR = "arg_zikr"

@AndroidEntryPoint
class ZikrDetailsFragment : Fragment() {

    private val mViewModel by viewModels<AzkarDetailsViewPagerItemViewModel>()

    private lateinit var binding: FragmentAzkarDetailsViewPagerItemBinding

    private lateinit var counterTextView: TextView
    private lateinit var topTimesTextView: TextView
    private lateinit var progressBar: ProgressBar

    private var zikr: Zikr? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zikr = it.getParcelable(ARG_ZIKR)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentAzkarDetailsViewPagerItemBinding.inflate(inflater, container, false)

        initViews()

        binding.textView.text = zikr?.name

        Log.i("TAG", "onCreateView:d ${zikr?.name}")

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

    companion object {
        fun createInstance(zikr: Zikr): ZikrDetailsFragment {

            return ZikrDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ZIKR, zikr)
                }
            }
        }
    }
}