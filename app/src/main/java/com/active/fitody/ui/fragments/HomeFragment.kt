package com.active.fitody.ui.BodyProportionsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitody.R
import com.active.fitody.TabNavigationHelper
import com.active.fitody.databinding.FragmentHomeBinding
import com.active.fitody.databinding.LayoutListItemBinding
import com.active.fitody.ui.fragments.BodyProportionsFragment
import com.active.fitody.ui.fragments.NotificationsFragment
import com.active.fitody.ui.fragments.SettingsFragment
import com.active.fitody.ui.fragments.WeightManagementFragment
import com.active.fitody.util.Enum
import com.active.fitody.util.Utilities
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshUserProfilePicture()
        val itemView1 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView1.tvItemText.text = "Weight"
        itemView1.tvItemValue.text = "86kg"
        itemView1.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.weight_item_icon))
        binding.llItemContainer.addView(itemView1.root)
        itemView1.root.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, WeightManagementFragment(), "WeightManagementFragment").addToBackStack("HomeFragment").commit()
        }
        val itemView2 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView2.tvItemText.text = "Body Proportions"
        itemView2.tvItemValue.text = "2/Aug/2021"
        itemView2.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_proportions))
        binding.llItemContainer.addView(itemView2.root)
        itemView2.root.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, BodyProportionsFragment(), "BodyProportionsFragment").addToBackStack("HomeFragment").commit()
        }
        val itemView3 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView3.tvItemText.text = "Exercise"
        itemView3.tvItemValue.text = "2/Aug/2021"
        itemView3.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_exercise))
        binding.llItemContainer.addView(itemView3.root)
        itemView3.root.setOnClickListener {
            TabNavigationHelper.getInstance().changeTab(Enum.bottomNavTabType.exercise)
        }
        val itemView5 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView5.tvItemText.text = "Sleep"
        itemView5.tvItemValue.text = "7 hr 15 Min"
        itemView5.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_sleep))
        binding.llItemContainer.addView(itemView5.root)

        binding.ibNotifButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, NotificationsFragment(), "NotificationsFragment").addToBackStack("HomeFragment").commit()
        }

        binding.btnHomeSettingsButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, SettingsFragment(), "SettingsFragment").addToBackStack("HomeFragment").commit()
        }

        binding.ivHomeUserProfile.setOnClickListener {
            TabNavigationHelper.getInstance().changeTab(Enum.bottomNavTabType.profile)
        }

    }

    private fun refreshUserProfilePicture() {
        Glide.with(this)
            .load(Utilities.instance.getProfilePicturePath())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.profile_picture_place_holder)
            .circleCrop()
            .into(binding.ivHomeUserProfile)
    }
}