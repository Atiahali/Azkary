package com.misbahah.categories.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.misbahah.data.model.Category
import com.misbahah.daysazkar.DayAzkarFragment
import com.misbahah.variousazkarlist.VariousAzkarListFragment

const val TIME_BOUND_AZKAR_PAGE_INDEX = 0
const val OPEN_AZKAR_PAGE_INDEX = 1

class CategoriesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        OPEN_AZKAR_PAGE_INDEX to { VariousAzkarListFragment() },
        TIME_BOUND_AZKAR_PAGE_INDEX to { DayAzkarFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}