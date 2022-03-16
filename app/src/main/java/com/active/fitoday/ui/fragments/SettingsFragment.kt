package com.active.fitoday.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.active.fitoday.R
import com.active.fitoday.databinding.FragmentSettingsBinding
import com.active.fitoday.databinding.LayoutSettingsListButtonItemBinding
import com.active.fitoday.databinding.LayoutSettingsListItemBinding
import com.active.fitoday.databinding.LayoutSettingsListShopButtonItemBinding
import com.active.fitoday.ui.activity.IntroLoginActivity
import com.active.fitoday.ui.model.settingsListItemDTO
import com.active.fitoday.ui.util.Enum

class SettingsFragment: Fragment(), SettingsListRecyclerViewAdapter.ItemClickListener {

    lateinit var settingsFragmentViewBinding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        settingsFragmentViewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return settingsFragmentViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = ArrayList<settingsListItemDTO>()
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.listItem, "Accounts & Settings"))
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.listItem, "Units"))
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.listItem, "Privacy and Legal Terms"))
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.listItem, "Help"))
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.listItem, "Rate Fitody"))
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.shopButtonItem, "Shop Fitody"))
        dataSource.add(settingsListItemDTO(Enum.settingsItemsType.logoutButtonItem, "Logout"))

        val listAdapter = SettingsListRecyclerViewAdapter(this)
        settingsFragmentViewBinding.rvSettingsItemsList.layoutManager = LinearLayoutManager(context)
        settingsFragmentViewBinding.rvSettingsItemsList.adapter = listAdapter
        listAdapter.setDataSource(dataSource)

        settingsFragmentViewBinding.ibSettingsBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    override fun logoutButtonClicked() {
        val intent = Intent(activity, IntroLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}

class SettingsListRecyclerViewAdapter(itemClickListener: ItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemsDataSource: ArrayList<settingsListItemDTO> = ArrayList()
    private var mItemType: Enum.settingsItemsType = Enum.settingsItemsType.listItem
    private var mItemClickListener: ItemClickListener = itemClickListener

    fun setDataSource(dataSource: ArrayList<settingsListItemDTO>)
    {
        mItemsDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        mItemType = mItemsDataSource[position].itemType
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (mItemType) {
            Enum.settingsItemsType.shopButtonItem -> {
                val binding = LayoutSettingsListShopButtonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SettingsShopButtonItemViewHolder(binding.root)
            }
            Enum.settingsItemsType.logoutButtonItem -> {
                val binding = LayoutSettingsListButtonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SettingsLogoutButtonItemViewHolder(binding.root)
            }
            else -> {
                val binding = LayoutSettingsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SettingsListItemViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SettingsListItemViewHolder) {
            val settingsItemHolder : SettingsListItemViewHolder = holder
            settingsItemHolder.itemView.findViewById<TextView>(R.id.tvSettingsItemText).text = mItemsDataSource[position].itemText
        }
        else if (holder is SettingsLogoutButtonItemViewHolder) {
            val settingsItemHolder : SettingsLogoutButtonItemViewHolder = holder
            settingsItemHolder.itemView.findViewById<Button>(R.id.btnLogout).setOnClickListener {
                mItemClickListener.logoutButtonClicked()
            }
        }
    }

    override fun getItemCount(): Int {
        return mItemsDataSource.size
    }

    interface ItemClickListener {
        fun logoutButtonClicked()
    }

}

class SettingsListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}

class SettingsShopButtonItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}

class SettingsLogoutButtonItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}