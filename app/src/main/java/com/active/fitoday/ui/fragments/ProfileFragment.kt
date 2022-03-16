package com.active.fitoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.active.fitoday.R
import com.active.fitoday.databinding.FragmentProfileBinding
import com.active.fitoday.databinding.LayoutProfileRowItemBinding
import com.active.fitoday.ui.model.ProfileItemInfoDTO
import com.bumptech.glide.Glide

class ProfileFragment: Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(resources.getDrawable(R.drawable.ic_default_profile_placeholder)).placeholder(R.drawable.ic_default_profile_placeholder).into(binding.ivPofilePageIcon)

        val itemsDataSource = ArrayList<ProfileItemInfoDTO>()
        itemsDataSource.add(ProfileItemInfoDTO("About You", "Gender", "Female", "Birthday", "Sep, 4, 1990", R.drawable.ic_profile_gender, R.drawable.ic_profile_birthday, true, false, true))
        itemsDataSource.add(ProfileItemInfoDTO("", "Weight", "105 lb", "Height", "5.6 ft", R.drawable.ic_profile_info_weight, R.drawable.ic_profile_info_height, true, true, false))
        itemsDataSource.add(ProfileItemInfoDTO("My Goals", "Daily Burned", "1000 Cal", "Daily Steps", "1233", null, null, false, false, true))
        itemsDataSource.add(ProfileItemInfoDTO("", "Weight", "105 lb", "Body Fat", "20%", null, null, false, true, false))
        itemsDataSource.add(ProfileItemInfoDTO("Bedtime Schedule", "Get in Bed", "10:30 PM", "Wake Up", "8:30 AM", null, null, false, false, true))

        val listAdapter = ProfileItemListRecyclerViewAdapter()
        binding.rvProfileItemInfoList.layoutManager = LinearLayoutManager(context)
        binding.rvProfileItemInfoList.adapter = listAdapter
        listAdapter.setDataSource(itemsDataSource)
    }
}

class ProfileItemListRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemsDataSource = ArrayList<ProfileItemInfoDTO>()

    public fun setDataSource(dataSource: ArrayList<ProfileItemInfoDTO>)
    {
        mItemsDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val profileItemListBinding = LayoutProfileRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileItemViewHolder(profileItemListBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProfileItemViewHolder) {
            (holder as ProfileItemViewHolder).bindItemInfo(mItemsDataSource[position])
        }
    }

    override fun getItemCount(): Int {
        return mItemsDataSource.size
    }
}

class ProfileItemViewHolder(val itemViewBinding: LayoutProfileRowItemBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
    fun bindItemInfo(itemInfo: ProfileItemInfoDTO)
    {
        itemViewBinding.tvProfileInfoHeading.text = itemInfo.profileItemTitle
        itemViewBinding.tvFirstProfileInfoTitle.text = itemInfo.profileFirstItemKey
        itemViewBinding.tvFirstProfileInfoText.text = itemInfo.profileFirstItemValue
        itemViewBinding.tvSecondProfileInfoTitle.text = itemInfo.profileSecondItemKey
        itemViewBinding.tvSecondProfileInfoText.text = itemInfo.profileSecondItemValue

        if (itemInfo.isItemIconRequired) {
            itemViewBinding.ivFirstProfileInfoTitleIcon.visibility = View.VISIBLE
            itemViewBinding.ivSecondProfileInfoTitleIcon.visibility = View.VISIBLE
            if (itemInfo.profileFirstItemIcon != null && itemInfo.profileSecondItemIcon != null) {
                itemViewBinding.ivFirstProfileInfoTitleIcon.setImageDrawable(itemViewBinding.root.resources.getDrawable(itemInfo.profileFirstItemIcon!!))
                itemViewBinding.ivSecondProfileInfoTitleIcon.setImageDrawable(itemViewBinding.root.resources.getDrawable(itemInfo.profileSecondItemIcon!!))
            }
        }
        else {
            itemViewBinding.ivFirstProfileInfoTitleIcon.visibility = View.GONE
            itemViewBinding.ivSecondProfileInfoTitleIcon.visibility = View.GONE
        }

        if (itemInfo.isLastItem) {
            itemViewBinding.llProfileItemSeparator.visibility = View.VISIBLE
        }
        else {
            itemViewBinding.llProfileItemSeparator.visibility = View.GONE
        }

        if (itemInfo.isTitleItem) {
            itemViewBinding.tvProfileInfoHeading.visibility = View.VISIBLE
        }
        else {
            itemViewBinding.tvProfileInfoHeading.visibility = View.GONE
        }


    }
}