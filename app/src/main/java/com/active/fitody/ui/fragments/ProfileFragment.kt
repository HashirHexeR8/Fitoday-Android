package com.active.fitody.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.active.fitody.R
import com.active.fitody.ServiceLocator
import com.active.fitody.databinding.FragmentProfileBinding
import com.active.fitody.databinding.LayoutProfileRowItemBinding
import com.active.fitody.model.ProfileItemInfoDTO
import com.active.fitody.util.Utilities
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var cameraResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>
    private val mUserManagementService = ServiceLocator.getUserManagementService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userInfo = mUserManagementService.getLoggedInUserInfo()

        binding.tvProfileUserName.text = userInfo.userName

        val itemsDataSource = ArrayList<ProfileItemInfoDTO>()
        itemsDataSource.add(ProfileItemInfoDTO("About You", "Gender", userInfo.userGender, "Birthday", userInfo.userBirthday, R.drawable.ic_profile_gender, R.drawable.ic_profile_birthday, true, false, true))
        itemsDataSource.add(ProfileItemInfoDTO("", "Weight", userInfo.userWeight, "Height", userInfo.userHeight, R.drawable.ic_profile_info_weight, R.drawable.ic_profile_info_height, true, true, false))
        itemsDataSource.add(ProfileItemInfoDTO("My Goals", "Daily Burned", "1000 Cal", "Daily Steps", "1233", null, null, false, false, true))
        itemsDataSource.add(ProfileItemInfoDTO("", "Weight", "105 lb", "Body Fat", "20%", null, null, false, true, false))
        itemsDataSource.add(ProfileItemInfoDTO("Bedtime Schedule", "Get in Bed", "10:30 PM", "Wake Up", "8:30 AM", null, null, false, false, true))

        val listAdapter = ProfileItemListRecyclerViewAdapter()
        binding.rvProfileItemInfoList.layoutManager = LinearLayoutManager(context)
        binding.rvProfileItemInfoList.adapter = listAdapter
        listAdapter.setDataSource(itemsDataSource)

        binding.ibCaptureProfilePicture.setOnClickListener {
            showImagePickOption()
        }

        cameraResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                Utilities.instance.storeUserProfilePicture(imageBitmap)
                refreshUserProfilePicture()
            }
        }

        galleryResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, result.data?.data)
                Utilities.instance.storeUserProfilePicture(imageBitmap)
                refreshUserProfilePicture()
            }
        }
        refreshUserProfilePicture()
    }

    private fun refreshUserProfilePicture() {
        Glide.with(this)
            .load(Utilities.instance.getProfilePicturePath())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.profile_picture_place_holder)
            .circleCrop()
            .into(binding.ivPofilePageIcon)
    }

    private fun showImagePickOption() {
        val adb: AlertDialog.Builder = AlertDialog.Builder(context)
        val items = arrayOf<CharSequence>("Camera", "Gallery")
        adb.setItems(items) { dialogInterface, optionChoosen ->
            if (optionChoosen == 0) {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraResultLauncher.launch(takePicture)
            }
            else {
                val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryResultLauncher.launch(pickPhoto)
            }
        }
        adb.setNegativeButton("Cancel", null)
        adb.setTitle("Choose image source")
        adb.show()
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