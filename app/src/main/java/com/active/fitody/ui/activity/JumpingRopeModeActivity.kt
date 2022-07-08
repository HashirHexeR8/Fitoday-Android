package com.active.fitody.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.model.data.ICSkipData
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.other.ICConstant
import com.active.fitody.FitodyDeviceHelper
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.ServiceLocator
import com.active.fitody.databinding.FragmentTargetedJumpingModeBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.model.SkipRopeActivityDataDTO
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum
import com.active.fitody.util.Utilities

class JumpingRopeModeActivity: AppCompatActivity(), FitodyDeviceHelper {

    lateinit var viewBinding: FragmentTargetedJumpingModeBinding
    var activityType: Enum.skipRopeActivityMode = Enum.skipRopeActivityMode.freeJumpMode
    var selectedTarget:Int = 0

    private val exerciseManagementService = ServiceLocator.getExerciseManagementService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityType = Enum.skipRopeActivityMode.enumFromInt(intent?.getIntExtra(Constants.jumpModeActivityTypeExtra, 1) ?: 1)
        val connectedDevice = intent.getParcelableExtra<BluetoothDeviceInfoDTO>(Constants.connectedDeviceInfoExtra)
        selectedTarget = intent.getIntExtra(Constants.targetNumberExtra, 25)


        val device = ICDevice()
        device.setMacAddr(connectedDevice?.deviceMacAddress)
        when (activityType) {
            Enum.skipRopeActivityMode.targetMode -> {
                ICDeviceManager.shared().settingManager.startSkipMode(device, ICConstant.ICSkipMode.ICSkipModeCount, selectedTarget) { status ->
                    status.name
                }
            }
            Enum.skipRopeActivityMode.countDownMode -> {
                ICDeviceManager.shared().settingManager.startSkipMode(device, ICConstant.ICSkipMode.ICSkipModeCount, selectedTarget) { status ->
                    status.name
                }
            }
        }

        viewBinding = FragmentTargetedJumpingModeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        when (activityType) {
            Enum.skipRopeActivityMode.targetMode -> {
                viewBinding.tvJumpRopeModeTitle.text = "Target Mode"
                viewBinding.tvTargetModeTargetText.text = "Target"
                viewBinding.tvTargetModeTargetCounter.text = "$selectedTarget"
                viewBinding.tvTargetModeJumpCounter.text = "0"
            }
            Enum.skipRopeActivityMode.countDownMode -> {
                viewBinding.tvJumpRopeModeTitle.text = "Countdown"
                viewBinding.tvTargetModeTargetText.text = "Ideal"
            }
        }

        viewBinding.ibrlTargetTopBarBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onReceivedSkipRopeData(skipData: ICSkipData) {
        super.onReceivedSkipRopeData(skipData)
        if (activityType == Enum.skipRopeActivityMode.countDownMode) {
            viewBinding.tvTargetModeJumpCounter.text = "${selectedTarget - (skipData.skip_count)}"
        }
        else {
            viewBinding.tvTargetModeJumpCounter.text = "${skipData.skip_count}"
        }
        viewBinding.tvSmartRopeCaloriesBurned.text = "${skipData.calories_burned}"
        viewBinding.tvSmartRopeTotalTime.text = Utilities.instance.getTimeInHMS(skipData.elapsed_time)

        if (skipData.isStabilized) {
            val skipActivityRecord = SkipRopeActivityDataDTO()
            skipActivityRecord.caloriesBurned = skipData.calories_burned
            skipActivityRecord.timeElapsed = skipData.elapsed_time
            skipActivityRecord.totalSkips = skipData.skip_count
            skipActivityRecord.skipMode = activityType
            skipActivityRecord.skippingActivityDate = Utilities.instance.getCurrentDate()
            exerciseManagementService.persistSkipRopeActivityData(skipActivityRecord)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        FitodyDeviceManager.getInstance().subscribeDeviceUpdates(this@JumpingRopeModeActivity)
    }

    override fun onPause() {
        super.onPause()
        FitodyDeviceManager.getInstance().unsubscribeDeviceUpdates()
    }

    override fun onBackPressed() {
        val device = ICDevice()
        device.setMacAddr(FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.skippingRope)?.deviceMacAddress)
        ICDeviceManager.shared().settingManager.stopSkip(device) {
            Log.i(Constants.DEVICE_SDK_LOG, "SkipRope${activityType.value} stopped.")
        }
        super.onBackPressed()
    }

}