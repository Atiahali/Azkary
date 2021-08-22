package com.misbahah.categories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.misbahah.R
import com.misbahah.daysazkar.DayAzkarViewModel
import com.misbahah.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    private val viewModel by viewModels<DayAzkarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = CategoriesPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        viewModel.getAllCategories()

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            OPEN_AZKAR_PAGE_INDEX -> R.drawable.open_azkar_tab_selector
            TIME_BOUND_AZKAR_PAGE_INDEX -> R.drawable.azkar_time_bound_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            OPEN_AZKAR_PAGE_INDEX -> getString(R.string.open_azkar_title)
            TIME_BOUND_AZKAR_PAGE_INDEX -> getString(R.string.time_bound_azkar_title)
            else -> null
        }
    }

}