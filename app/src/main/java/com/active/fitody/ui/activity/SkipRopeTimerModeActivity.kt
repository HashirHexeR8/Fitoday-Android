package com.active.fitody.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.model.data.ICSkipData
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.other.ICConstant
import com.active.fitody.FitodyDeviceHelper
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.ServiceLocator
import com.active.fitody.databinding.ActivitySkipRopeTimerModeBinding
import com.active.fitody.databinding.FragmentTargetedJumpingModeBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.model.SkipRopeActivityDataDTO
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum
import com.active.fitody.util.Utilities

class SkipRopeTimerModeActivity: AppCompatActivity(), FitodyDeviceHelper {

    lateinit var viewBinding: ActivitySkipRopeTimerModeBinding
    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var activityType: Enum.skipRopeActivityMode = Enum.skipRopeActivityMode.freeJumpMode

    private val exerciseManagementService = ServiceLocator.getExerciseManagementService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewBinding.ibStopTimerButton.performClick()
            }
        }

        activityType = Enum.skipRopeActivityMode.enumFromInt(intent?.getIntExtra(Constants.jumpModeActivityTypeExtra, 1) ?: 1)

        viewBinding = ActivitySkipRopeTimerModeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.ibrlTargetTopBarBack.setOnClickListener {
            onBackPressed()
        }

        viewBinding.ibStartTimerButton.setOnClickListener {
            viewBinding.edMinuteCount.isFocusable = false
            viewBinding.edMinuteCount.isFocusableInTouchMode = false
            viewBinding.edSecondCount.isFocusable = false
            viewBinding.edSecondCount.isFocusableInTouchMode = false
            val totalSeconds = viewBinding.edMinuteCount.text.toString().toInt() * 60 + viewBinding.edSecondCount.text.toString().toInt()
            val intent = Intent(this@SkipRopeTimerModeActivity, FreeJumpModeActivity::class.java)
            intent.putExtra(Constants.freeJumpModeTimerExtra, totalSeconds)
            resultLauncher.launch(intent)
        }

        viewBinding.ibStopTimerButton.setOnClickListener {
            viewBinding.edMinuteCount.isFocusable = true
            viewBinding.edMinuteCount.isFocusableInTouchMode = true
            viewBinding.edSecondCount.isFocusable = true
            viewBinding.edSecondCount.isFocusableInTouchMode = true
            val device = ICDevice()
            device.setMacAddr(FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.skippingRope)?.deviceMacAddress)
            ICDeviceManager.shared().settingManager.stopSkip(device) {
                Log.i(Constants.DEVICE_SDK_LOG, "SkipTimerModeStopped")
            }
        }

        viewBinding.edMinuteCount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.edMinuteCount.text.isNotEmpty()) {
                    val minutes = viewBinding.edMinuteCount.text.toString().toInt()
                    if (minutes > 59) {
                        viewBinding.edMinuteCount.setText("59")
                    }
                }

            }
        })

        viewBinding.edSecondCount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.edSecondCount.text.isNotEmpty()) {
                    val seconds = viewBinding.edSecondCount.text.toString().toInt()
                    if (seconds > 59) {
                        viewBinding.edSecondCount.setText("59")
                    }
                }
            }
        })
    }

    override fun onReceivedSkipRopeData(skipData: ICSkipData) {
        super.onReceivedSkipRopeData(skipData)

        if (skipData.isStabilized) {
            val skipActivityRecord = SkipRopeActivityDataDTO()
            skipActivityRecord.caloriesBurned = skipData.calories_burned
            skipActivityRecord.timeElapsed = skipData.elapsed_time
            skipActivityRecord.totalSkips = skipData.skip_count
            skipActivityRecord.skipMode = activityType
            skipActivityRecord.skippingActivityDate = Utilities.instance.getCurrentDate()
            exerciseManagementService.persistSkipRopeActivityData(skipActivityRecord)
        }
    }

    override fun onResume() {
        super.onResume()
        FitodyDeviceManager.getInstance().subscribeDeviceUpdates(this@SkipRopeTimerModeActivity)
    }

    override fun onPause() {
        super.onPause()
        FitodyDeviceManager.getInstance().unsubscribeDeviceUpdates()
    }

}