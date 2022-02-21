package com.active.fitoday.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.active.fitoday.databinding.FragmentBodyProportionsBinding
import com.active.fitoday.databinding.LayoutBodyProportionsRecylcerViewItemBinding
import com.active.fitoday.databinding.LayoutRightBodyProportionsRecyclerViewItemBinding
import com.active.fitoday.ui.model.BodyProportionsItemInfoDTO
import com.active.fitoday.ui.util.Enum

class BodyProportionsFragment: Fragment() {

    lateinit var binding: FragmentBodyProportionsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBodyProportionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leftDataSource = ArrayList<BodyProportionsItemInfoDTO>()
        leftDataSource.add(BodyProportionsItemInfoDTO("Neck", "17.5 inch"))
        leftDataSource.add(BodyProportionsItemInfoDTO("Bust", ""))
        leftDataSource.add(BodyProportionsItemInfoDTO("Abdonen", ""))
        leftDataSource.add(BodyProportionsItemInfoDTO("L:Biceps", ""))
        leftDataSource.add(BodyProportionsItemInfoDTO("L:Thigh", ""))
        leftDataSource.add(BodyProportionsItemInfoDTO("L:Calf", ""))
        val rightDataSource = ArrayList<BodyProportionsItemInfoDTO>()
        rightDataSource.add(BodyProportionsItemInfoDTO("Shoulder", "17.5 inch"))
        rightDataSource.add(BodyProportionsItemInfoDTO("Waist", ""))
        rightDataSource.add(BodyProportionsItemInfoDTO("Hip", ""))
        rightDataSource.add(BodyProportionsItemInfoDTO("R:Biceps", ""))
        rightDataSource.add(BodyProportionsItemInfoDTO("R:Thigh", ""))
        rightDataSource.add(BodyProportionsItemInfoDTO("R:Calf", ""))
        val leftListAdapter = BodyProportionsRecyclerViewAdapter(Enum.bodyProportionsType.leftProportionItem)
        binding.rvLeftBodyItems.layoutManager = LinearLayoutManager(context)
        binding.rvLeftBodyItems.adapter = leftListAdapter
        leftListAdapter.setDataSource(leftDataSource)
        val rightListAdapter = BodyProportionsRecyclerViewAdapter(Enum.bodyProportionsType.rightProportionItem)
        binding.rvRightBodyItems.layoutManager = LinearLayoutManager(context)
        binding.rvRightBodyItems.adapter = rightListAdapter
        rightListAdapter.setDataSource(rightDataSource)

        binding.ibProportionsBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}

class BodyProportionsRecyclerViewAdapter(itemType: Enum.bodyProportionsType): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mItemType = itemType
    var mItemsDataSource = ArrayList<BodyProportionsItemInfoDTO>()

    public fun setDataSource(dataSource: ArrayList<BodyProportionsItemInfoDTO>)
    {
        mItemsDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mItemType == Enum.bodyProportionsType.leftProportionItem) {
            val binding = LayoutBodyProportionsRecylcerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LeftBodyProportionItemViewHolder(binding)
        }
        else {
            val binding = LayoutRightBodyProportionsRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RightBodyProportionItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is LeftBodyProportionItemViewHolder) {
            (holder as LeftBodyProportionItemViewHolder).bindItemInfo(mItemsDataSource[position])
        }
        else if (holder is RightBodyProportionItemViewHolder) {
            (holder as RightBodyProportionItemViewHolder).bindItemInfo(mItemsDataSource[position])
        }

    }

    override fun getItemCount(): Int {
        return mItemsDataSource.size
    }

}

class LeftBodyProportionItemViewHolder(val itemViewBinding: LayoutBodyProportionsRecylcerViewItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {

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

class RightBodyProportionItemViewHolder(val itemViewBinding: LayoutRightBodyProportionsRecyclerViewItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
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