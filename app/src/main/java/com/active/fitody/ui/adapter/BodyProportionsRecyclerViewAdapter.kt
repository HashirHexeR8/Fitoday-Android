package com.active.fitody.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.active.fitody.databinding.LayoutBodyProportionsRecylcerViewItemBinding
import com.active.fitody.databinding.LayoutRightBodyProportionsRecyclerViewItemBinding
import com.active.fitody.model.BodyProportionsItemInfoDTO
import com.active.fitody.util.Enum

class BodyProportionsRecyclerViewAdapter(itemType: Enum.bodyProportionsType, private val onItemTapped: (itemInfo: BodyProportionsItemInfoDTO) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mItemType = itemType
    var mItemsDataSource = ArrayList<BodyProportionsItemInfoDTO>()

    public fun setDataSource(dataSource: ArrayList<BodyProportionsItemInfoDTO>)
    {
        mItemsDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (mItemType == Enum.bodyProportionsType.leftProportionItem) {
            val binding = LayoutBodyProportionsRecylcerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LeftBodyProportionItemViewHolder(binding)
        } else {
            val binding = LayoutRightBodyProportionsRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RightBodyProportionItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = mItemsDataSource[position]
        if (holder is LeftBodyProportionItemViewHolder) {
            val leftItemViewHolder = holder as LeftBodyProportionItemViewHolder
            leftItemViewHolder.bindItemInfo(item)
            leftItemViewHolder.itemView.setOnClickListener {
                onItemTapped(item)
            }

        }
        else if (holder is RightBodyProportionItemViewHolder) {
            val leftItemViewHolder = holder as RightBodyProportionItemViewHolder
            leftItemViewHolder.bindItemInfo(item)
            leftItemViewHolder.itemView.setOnClickListener {
                onItemTapped(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return mItemsDataSource.size
    }

    inner class LeftBodyProportionItemViewHolder(val itemViewBinding: LayoutBodyProportionsRecylcerViewItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bindItemInfo(itemInfo: BodyProportionsItemInfoDTO) {
            if (itemInfo.bodyPartMeasurementValue.isEmpty()) {
                itemViewBinding.tvBodyProportionLeftItemHeading.text = itemInfo.bodyPartProportionItemName
                itemViewBinding.llBodyProportionsLeftItemContainer.visibility = View.GONE
                itemViewBinding.tvBodyProportionLeftItemHeading.visibility = View.VISIBLE
            }
            else {
                itemViewBinding.llBodyProportionsLeftItemContainer.visibility = View.VISIBLE
                itemViewBinding.tvBodyProportionLeftItemHeading.visibility = View.GONE
                itemViewBinding.tvBodyProportionLeftItemName.text = itemInfo.bodyPartProportionItemName
                itemViewBinding.tvBodyProportionLeftItemValue.text = itemInfo.bodyPartMeasurementValue
            }
        }
    }

    inner class RightBodyProportionItemViewHolder(val itemViewBinding: LayoutRightBodyProportionsRecyclerViewItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bindItemInfo(itemInfo: BodyProportionsItemInfoDTO) {
            if (itemInfo.bodyPartMeasurementValue.isEmpty()) {
                itemViewBinding.tvBodyProportionRightItemHeading.text = itemInfo.bodyPartProportionItemName
                itemViewBinding.llBodyProportionsRightItemContainer.visibility = View.GONE
                itemViewBinding.tvBodyProportionRightItemHeading.visibility = View.VISIBLE
            }
            else {
                itemViewBinding.llBodyProportionsRightItemContainer.visibility = View.VISIBLE
                itemViewBinding.tvBodyProportionRightItemHeading.visibility = View.GONE
                itemViewBinding.tvBodyProportionRightItemName.text = itemInfo.bodyPartProportionItemName
                itemViewBinding.tvBodyProportionRightItemValue.text = itemInfo.bodyPartMeasurementValue
            }
        }
    }

}