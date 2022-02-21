package com.active.fitoday.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.active.fitoday.ui.BodyProportionsFragment.IntroViewPagerFragment
import com.active.fitoday.ui.util.Enum

class IntroViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val pageType = Enum.IntroPagerEnum.enumFromInt(position)
        return IntroViewPagerFragment(pageType)
    }
}