package org.azkary.azkardetails.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.azkary.data.model.Zikr
import org.azkary.databinding.FragmentAzkarDetailsViewPagerItemBinding


private const val ARG_ZIKR = "arg_zikr"

@AndroidEntryPoint
class AzkarDetailsViewPagerItemFragment : Fragment() {

    private val mViewModel by viewModels<AzkarDetailsViewPagerItemViewModel>()

    private lateinit var binding: FragmentAzkarDetailsViewPagerItemBinding

    private lateinit var zikr: Zikr

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zikr = it.getParcelable(ARG_ZIKR)!!
        }
        mViewModel.getCurrentTimeByZikrName(zikr)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentAzkarDetailsViewPagerItemBinding.inflate(inflater, container, false)

        initViews()

        mViewModel.currentTime.observe(requireActivity(), { currentValue: Int ->
            binding.timer.text = currentValue.toString()
            if (zikr.repeatingNumber > 0)
                binding.progressBar.progress = currentValue.toBigInteger().toFloat()
        })

        return binding.root
    }


    private fun initViews() {
        binding.thekrDetailsFragment = this
        binding.model = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.zikr = zikr
    }

    fun incrementCounterByOne() {
        val incrementedValue = (binding.timer.text.toString().toInt() + 1)
        if (zikr.repeatingNumber <= 0) {
            mViewModel.incrementCounterByOne(incrementedValue)
            if (incrementedValue % 100 == 0) {
                mViewModel.playDoneRingtoneWithVibration(requireContext())
            }
        }
        if (incrementedValue <= zikr.repeatingNumber) {
            mViewModel.incrementCounterByOne(incrementedValue)
            if (incrementedValue == zikr.repeatingNumber) {
                mViewModel.playDoneRingtoneWithVibration(requireContext())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.setCurrentTimeByZikrName(binding.timer.text.toString().toInt(), zikr)
    }


    companion object {
        fun createInstance(zikr: Zikr): AzkarDetailsViewPagerItemFragment {
            return AzkarDetailsViewPagerItemFragment().apply {
                arguments = Bundle(1).apply {
                    putParcelable(ARG_ZIKR, zikr)
                }
            }
        }
    }
}