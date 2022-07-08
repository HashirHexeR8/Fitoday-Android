package com.active.fitody

import com.active.fitody.util.Enum
import com.google.android.material.tabs.TabLayout

class TabNavigationHelper {

    private var mTabLayout: TabLayout? = null

    companion object {
        private var instance: TabNavigationHelper? = null

        fun getInstance(): TabNavigationHelper {
            if (instance == null) {
                instance = TabNavigationHelper()
            }
            return instance!!
        }
    }

    fun setTabView (tabLayout: TabLayout) {
        mTabLayout = tabLayout
    }

    fun changeTab(tabType: Enum.bottomNavTabType) {
        val tab = mTabLayout?.getTabAt(tabType.value)
        tab?.select()
    }
}