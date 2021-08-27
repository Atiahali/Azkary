package com.misbahah.azkardetails.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.misbahah.azkardetails.item.AzkarDetailsViewPagerItemFragment
import com.misbahah.data.model.Zikr

class AzkarDetailsViewPagerAdapter(fragment: Fragment, private val list: List<Zikr>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        list.size


    override fun createFragment(position: Int): Fragment =
        AzkarDetailsViewPagerItemFragment.createInstance(list[position])

}