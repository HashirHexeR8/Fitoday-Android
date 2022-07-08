package com.active.fitody.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.active.fitody.R
import com.active.fitody.TabNavigationHelper
import com.active.fitody.databinding.ActivityHomeDashboardBinding
import com.active.fitody.ui.BodyProportionsFragment.HomeFragment
import com.active.fitody.ui.fragments.DevicesFragment
import com.active.fitody.ui.fragments.ExerciseFragment
import com.active.fitody.ui.fragments.ProfileFragment
import com.active.fitody.util.Enum
import com.google.android.material.tabs.TabLayout

class HomeDashboardActivity: AppCompatActivity() {

    lateinit var binding: ActivityHomeDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TabNavigationHelper.getInstance().setTabView(binding.bottomTabLayout)

        val homeTab = binding.bottomTabLayout.newTab()
        homeTab.setCustomView(R.layout.bottom_nav_home)
        binding.bottomTabLayout.addTab(homeTab)

        val exerciseTab = binding.bottomTabLayout.newTab()
        exerciseTab.setCustomView(R.layout.bottom_nav_exercise)
        binding.bottomTabLayout.addTab(exerciseTab)

        val deviceTab = binding.bottomTabLayout.newTab()
        deviceTab.setCustomView(R.layout.bottom_nav_device)
        binding.bottomTabLayout.addTab(deviceTab)

        val profileTab = binding.bottomTabLayout.newTab()
        profileTab.setCustomView(R.layout.bottom_nav_profile)
        binding.bottomTabLayout.addTab(profileTab)

        val tabClickListener = object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab?.position == Enum.bottomNavTabType.home.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, HomeFragment(), "HomeFragment").commit()
                }
                else if (tab?.position == Enum.bottomNavTabType.exercise.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, ExerciseFragment(), "ExerciseFragment").commit()
                }
                else if (tab?.position == Enum.bottomNavTabType.devices.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, DevicesFragment(), "DevicesFragment").commit()
                }
                else if (tab?.position == Enum.bottomNavTabType.profile.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, ProfileFragment(), "ProfileFragment").commit()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                if (tab?.position == Enum.bottomNavTabType.home.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, HomeFragment(), "HomeFragment").commit()
                }
                else if (tab?.position == Enum.bottomNavTabType.exercise.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, ExerciseFragment(), "ExerciseFragment").commit()
                }
                else if (tab?.position == Enum.bottomNavTabType.devices.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, DevicesFragment(), "DevicesFragment").commit()
                }
                else if (tab?.position == Enum.bottomNavTabType.profile.value)
                {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.tabFragmentContainer.id, ProfileFragment(), "ProfileFragment").commit()
                }
            }
        }
        binding.bottomTabLayout.addOnTabSelectedListener(tabClickListener)
        homeTab.select()
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}