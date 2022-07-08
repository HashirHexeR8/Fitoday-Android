package com.active.fitody.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.model.data.ICRulerData
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.other.ICConstant
import com.active.fitody.FitodyDeviceHelper
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.ServiceLocator
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.FragmentBodyProportionsBinding
import com.active.fitody.databinding.LayoutRightBodyProportionsRecyclerViewItemBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.model.BodyProportionsItemInfoDTO
import com.active.fitody.services.BodyProportionsManagementService
import com.active.fitody.ui.activity.ConnectDeviceActivity
import com.active.fitody.ui.adapter.BodyProportionsRecyclerViewAdapter
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum

class BodyProportionsFragment: Fragment(), FitodyDeviceHelper {

    lateinit var binding: FragmentBodyProportionsBinding
    private val leftListAdapter = BodyProportionsRecyclerViewAdapter(Enum.bodyProportionsType.leftProportionItem)
    { itemInfo: BodyProportionsItemInfoDTO -> onBodyProportionItemClick(itemInfo) }
    private val rightListAdapter = BodyProportionsRecyclerViewAdapter(Enum.bodyProportionsType.rightProportionItem)
    { itemInfo: BodyProportionsItemInfoDTO -> onBodyProportionItemClick(itemInfo) }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var selectedBodyPart: BodyProportionsItemInfoDTO? = null
    private var connectDeviceInfo: BluetoothDeviceInfoDTO? = null
    private var isListeningForData = false

    private var bodyProportionsService = ServiceLocator.getBodyProportionsManagementService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBodyProportionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDeviceInfo = FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.rular)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.tvDeviceNotConnectedText.visibility = View.GONE
                connectDeviceInfo = result.data?.getParcelableExtra<BluetoothDeviceInfoDTO>(Constants.connectedDeviceInfoExtra)
                SelectUnitBottomSheetDialogFragment.shareInstance(Enum.deviceType.rular).show(childFragmentManager, "SelectUni")
            }

        }

        if (connectDeviceInfo != null) {
            binding.tvDeviceNotConnectedText.visibility = View.GONE
        }
        else {
            binding.tvDeviceNotConnectedText.visibility = View.VISIBLE
        }

        val leftDataSource = ArrayList<BodyProportionsItemInfoDTO>()
        leftDataSource.add(
            BodyProportionsItemInfoDTO(
                "Neck",
                "",
                Enum.bodyPartTypeEnum.neck,
                Enum.bodyProportionsType.leftProportionItem
            )
        )
        leftDataSource.add(
            BodyProportionsItemInfoDTO(
                "Bust",
                "",
                Enum.bodyPartTypeEnum.bust,
                Enum.bodyProportionsType.leftProportionItem
            )
        )
        leftDataSource.add(
            BodyProportionsItemInfoDTO(
                "Abdonen",
                "",
                Enum.bodyPartTypeEnum.abdonen,
                Enum.bodyProportionsType.leftProportionItem
            )
        )
        leftDataSource.add(
            BodyProportionsItemInfoDTO(
                "L:Biceps",
                "",
                Enum.bodyPartTypeEnum.bicep,
                Enum.bodyProportionsType.leftProportionItem
            )
        )
        leftDataSource.add(
            BodyProportionsItemInfoDTO(
                "L:Thigh",
                "",
                Enum.bodyPartTypeEnum.thigh,
                Enum.bodyProportionsType.leftProportionItem
            )
        )
        leftDataSource.add(BodyProportionsItemInfoDTO("L:Calf", "", Enum.bodyPartTypeEnum.calf, Enum.bodyProportionsType.leftProportionItem))
        val rightDataSource = ArrayList<BodyProportionsItemInfoDTO>()
        rightDataSource.add(
            BodyProportionsItemInfoDTO(
                "Shoulder",
                "",
                Enum.bodyPartTypeEnum.shoulder,
                Enum.bodyProportionsType.rightProportionItem
            )
        )
        rightDataSource.add(
            BodyProportionsItemInfoDTO(
                "Waist",
                "",
                Enum.bodyPartTypeEnum.waist,
                Enum.bodyProportionsType.rightProportionItem
            )
        )
        rightDataSource.add(
            BodyProportionsItemInfoDTO(
                "Hip",
                "",
                Enum.bodyPartTypeEnum.hip,
                Enum.bodyProportionsType.rightProportionItem
            )
        )
        rightDataSource.add(
            BodyProportionsItemInfoDTO(
                "R:Biceps",
                "",
                Enum.bodyPartTypeEnum.bicep,
                Enum.bodyProportionsType.rightProportionItem
            )
        )
        rightDataSource.add(
            BodyProportionsItemInfoDTO(
                "R:Thigh",
                "",
                Enum.bodyPartTypeEnum.thigh,
                Enum.bodyProportionsType.rightProportionItem
            )
        )
        rightDataSource.add(
            BodyProportionsItemInfoDTO(
                "R:Calf",
                "",
                Enum.bodyPartTypeEnum.calf,
                Enum.bodyProportionsType.rightProportionItem
            )
        )
        binding.rvLeftBodyItems.layoutManager = LinearLayoutManager(context)
        binding.rvLeftBodyItems.adapter = leftListAdapter
        leftListAdapter.setDataSource(leftDataSource)

        binding.rvRightBodyItems.layoutManager = LinearLayoutManager(context)
        binding.rvRightBodyItems.adapter = rightListAdapter
        rightListAdapter.setDataSource(rightDataSource)

        binding.ibProportionsBack.setOnClickListener {
            FitodyDeviceManager.getInstance().setConnectedDevice(null)
            activity?.onBackPressed()
        }

        binding.ibProportionsSettings.setOnClickListener {
            SelectUnitBottomSheetDialogFragment.shareInstance(Enum.deviceType.rular).show(childFragmentManager, "SelectUnitFragment")
        }
        getBodyProportionsRecord()
    }

    fun getBodyProportionsRecord() {
        bodyProportionsService.getProportionsData().forEach { item ->
            if (item.bodyProportionType == Enum.bodyProportionsType.leftProportionItem) {
                leftListAdapter.mItemsDataSource.find { it.bodyPartType == item.bodyPartType }?.bodyPartMeasurementValue = item.bodyPartMeasurementValue
                val indexOfItem = leftListAdapter.mItemsDataSource.indexOfFirst { it.bodyPartType == item.bodyPartType }
                leftListAdapter.notifyItemChanged(indexOfItem)
            }
            else {
                rightListAdapter.mItemsDataSource.find { it.bodyPartType == item.bodyPartType }?.bodyPartMeasurementValue = item.bodyPartMeasurementValue
                val indexOfItem = rightListAdapter.mItemsDataSource.indexOfFirst { it.bodyPartType == item.bodyPartType }
                rightListAdapter.notifyItemChanged(indexOfItem)
            }
        }

    }

    override fun onReceivedMeasuringScaleData(measureData: ICRulerData) {
        super.onReceivedMeasuringScaleData(measureData)
        if (measureData.isStabilized && isListeningForData) {
            isListeningForData = false
            selectedBodyPart?.bodyPartMeasurementValue = if (UserPrefs.getInstance().getUserTapeUnit() == Enum.userRularUnit.inch) {
                 measureData.distance_in.toString() + " inch"
            }
            else {
                measureData.distance_cm.toString() + " cm"
            }
            if (selectedBodyPart?.bodyProportionType == Enum.bodyProportionsType.leftProportionItem) {
                val itemIndex = leftListAdapter.mItemsDataSource.indexOfFirst { item ->
                    item.bodyPartType == selectedBodyPart?.bodyPartType
                }
                leftListAdapter.notifyItemChanged(itemIndex)
            }
            else if (selectedBodyPart?.bodyProportionType == Enum.bodyProportionsType.rightProportionItem) {
                val itemIndex = rightListAdapter.mItemsDataSource.indexOfFirst { item ->
                    item.bodyPartType == selectedBodyPart?.bodyPartType
                }
                rightListAdapter.notifyItemChanged(itemIndex)
            }
            selectedBodyPart?.let { bodyProportionsService.persistBodyProportionsData(it) }
            ReadDeviceDataBottomSheetFragment.shareInstance(Enum.deviceType.rular).dismiss()
            getBodyProportionsRecord()
        }
    }

    override fun onResume() {
        super.onResume()
        FitodyDeviceManager.getInstance().subscribeDeviceUpdates(this)
    }

    override fun onPause() {
        super.onPause()
        FitodyDeviceManager.getInstance().unsubscribeDeviceUpdates()
    }

    private fun onBodyProportionItemClick(itemInfo: BodyProportionsItemInfoDTO) {
        selectedBodyPart = itemInfo
        checkForDeviceConnection()
    }

    private fun checkForDeviceConnection() {
        if (connectDeviceInfo == null || connectDeviceInfo?.deviceType != Enum.deviceType.rular) {
            val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Fitody")
            alertDialog.setMessage("No device connected, Please connect to a device.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener {
                        dialog, which -> dialog.dismiss()
                    val intent = Intent(activity, ConnectDeviceActivity::class.java)
                    intent.putExtra(Constants.connectDeviceTypeExtra, Enum.deviceType.rular.value)
                    resultLauncher.launch(intent)
                })
            alertDialog.show()
        }
        else {
            isListeningForData = true
            val device = ICDevice()
            device.setMacAddr(connectDeviceInfo?.deviceMacAddress)
            if (UserPrefs.getInstance().getUserTapeUnit() == Enum.userRularUnit.inch) {
                ICDeviceManager.shared().settingManager.setRulerUnit(device, ICConstant.ICRulerUnit.ICRulerUnitInch) { settingResult ->
                    Log.i(Constants.DEVICE_SDK_LOG, "Ruler Unit Changed$settingResult")
                }
            }
            else {
                ICDeviceManager.shared().settingManager.setRulerUnit(device, ICConstant.ICRulerUnit.ICRulerUnitCM) { settingResult ->
                    Log.i(Constants.DEVICE_SDK_LOG, "Ruler Unit Changed$settingResult")
                }
            }
            ReadDeviceDataBottomSheetFragment.shareInstance(Enum.deviceType.rular).show(childFragmentManager, "ReadDeviceData")
        }
    }
}