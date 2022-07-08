package com.active.fitody.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.model.data.ICSkipData
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.other.ICConstant
import com.active.fitody.databinding.FragmentFreeJumpModeBinding
import com.active.fitody.FitodyDeviceHelper
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.ServiceLocator
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.model.SkipRopeActivityDataDTO
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum
import com.active.fitody.util.Utilities

class FreeJumpModeActivity:AppCompatActivity(), FitodyDeviceHelper {
    private lateinit var viewBinding: FragmentFreeJumpModeBinding

    private val skipActivityRecord = SkipRopeActivityDataDTO()
    private val exerciseManagementService = ServiceLocator.getExerciseManagementService()
    private var isTimerModeActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentFreeJumpModeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.ibFreeJumpModeTopBarBack.setOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra(Constants.freeJumpModeTimerExtra)) {
            isTimerModeActive = true
            val totalSeconds = intent.getIntExtra(Constants.freeJumpModeTimerExtra, 0)
            val connectedDevice = FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.skippingRope)
            val device = ICDevice()
            device.setMacAddr(connectedDevice?.deviceMacAddress)
            ICDeviceManager.shared().settingManager.startSkipMode(device, ICConstant.ICSkipMode.ICSkipModeTiming, totalSeconds) { status ->
                status.name
            }
            viewBinding.llFreeJumpTimeTrackerContainer.visibility = View.VISIBLE
            viewBinding.tvFreeJumpModeTotalTime.text = Utilities.instance.getTimeInHMS(totalSeconds)
        }
        else {
            val connectedDevice = FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.skippingRope)
            val device = ICDevice()
            device.setMacAddr(connectedDevice?.deviceMacAddress)
            ICDeviceManager.shared().settingManager.startSkipMode(device, ICConstant.ICSkipMode.ICSkipModeFreedom, 10) { status ->
                status.name
            }
            viewBinding.llFreeJumpTimeTrackerContainer.visibility = View.GONE
        }


    }

    override fun onReceivedSkipRopeData(skipData: ICSkipData) {
        super.onReceivedSkipRopeData(skipData)

        viewBinding.tvFreeJumpModeTargetCounter.text = skipData.skip_count.toString()
        viewBinding.tvFreeJumpModeTotalJumps.text = Utilities.instance.getTimeInHMS(skipData.elapsed_time)
        viewBinding.tvFreeJumpModeCaloriesBurned.text = skipData.calories_burned.toString()

        skipActivityRecord.caloriesBurned = skipData.calories_burned
        skipActivityRecord.timeElapsed = skipData.elapsed_time
        skipActivityRecord.totalSkips = skipData.skip_count
        skipActivityRecord.skipMode = Enum.skipRopeActivityMode.freeJumpMode
        skipActivityRecord.skippingActivityDate = Utilities.instance.getCurrentDate()

        if (isTimerModeActive) {
            if (skipData.isStabilized) {
                finish()
            }
        }


    }

    override fun onResume() {
        super.onResume()
        FitodyDeviceManager.getInstance().subscribeDeviceUpdates(this@FreeJumpModeActivity)
    }

    override fun onPause() {
        super.onPause()
        FitodyDeviceManager.getInstance().unsubscribeDeviceUpdates()
    }

    override fun onStop() {
        super.onStop()
        if (skipActivityRecord.totalSkips != 0) {
            exerciseManagementService.persistSkipRopeActivityData(skipActivityRecord)
        }
    }

    override fun onBackPressed() {
        val device = ICDevice()
        device.setMacAddr(FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.skippingRope)?.deviceMacAddress)
        ICDeviceManager.shared().settingManager.stopSkip(device) {
            Log.i(Constants.DEVICE_SDK_LOG, "SkipFreedomModeStopped")
        }
        super.onBackPressed()
    }
}