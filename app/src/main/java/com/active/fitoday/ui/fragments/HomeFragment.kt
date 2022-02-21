package com.active.fitoday.ui.BodyProportionsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.R
import com.active.fitoday.databinding.FragmentHomeBinding
import com.active.fitoday.databinding.LayoutListItemBinding
import com.active.fitoday.ui.fragments.BodyProportionsFragment
import com.active.fitoday.ui.fragments.NotificationsFragment
import com.active.fitoday.ui.fragments.SettingsFragment
import com.active.fitoday.ui.fragments.WeightManagementFragment

class HomeFragment: Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        itemView2.tvItemText.text = "Proportions"
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
        val itemView4 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView4.tvItemText.text = "Heart Rate"
        itemView4.tvItemValue.text = "55"
        itemView4.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_heart_rate))
        binding.llItemContainer.addView(itemView4.root)
        val itemView5 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView5.tvItemText.text = "Sleep"
        itemView5.tvItemValue.text = "7 hr 15 Min"
        itemView5.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_sleep))
        binding.llItemContainer.addView(itemView5.root)
        val itemView6 = LayoutListItemBinding.inflate(layoutInflater, binding.llItemContainer, false)
        itemView6.tvItemText.text = "Blood Pressure"
        itemView6.tvItemValue.text = "120/80"
        itemView5.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_heart_rate))
        binding.llItemContainer.addView(itemView6.root)

        binding.ibNotifButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, NotificationsFragment(), "NotificationsFragment").addToBackStack("HomeFragment").commit()
        }

        binding.btnHomeSettingsButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, SettingsFragment(), "SettingsFragment").addToBackStack("HomeFragment").commit()
        }

    }
}