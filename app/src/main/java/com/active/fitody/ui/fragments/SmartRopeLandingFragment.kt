package com.active.fitody.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.ServiceLocator
import com.active.fitody.databinding.FragmentSmartRopeLandingBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.ui.activity.*
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum
import com.active.fitody.util.Utilities


class SmartRopeLandingFragment: Fragment() {

    lateinit var viewBinding: FragmentSmartRopeLandingBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var skippingActivityResultLauncher: ActivityResultLauncher<Intent>
    private var connectDeviceInfo: BluetoothDeviceInfoDTO? = null
    private val exerciseManagementService = ServiceLocator.getExerciseManagementService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentSmartRopeLandingBinding.inflate(inflater)
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDeviceInfo = FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.skippingRope)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                connectDeviceInfo = result.data?.getParcelableExtra<BluetoothDeviceInfoDTO>(Constants.connectedDeviceInfoExtra)
            }
        }

        skippingActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            setSkipHistoryData()
        }

        viewBinding.btnTargetMode.setOnClickListener {
            checkDeviceConnectionStatus(Enum.skipRopeActivityMode.targetMode)
        }

        viewBinding.btnCountDown.setOnClickListener {
            checkDeviceConnectionStatus(Enum.skipRopeActivityMode.countDownMode)
        }

        viewBinding.btnFreeJumpMode.setOnClickListener {
            checkDeviceConnectionStatus(Enum.skipRopeActivityMode.freeJumpMode)
        }

        viewBinding.btnTimerMode.setOnClickListener {
            checkDeviceConnectionStatus(Enum.skipRopeActivityMode.timerMode)
        }

        viewBinding.ibSmartRopeSettings.setOnClickListener {
            val intent = Intent(activity, ConnectDeviceActivity::class.java)
            resultLauncher.launch(intent)
        }

        viewBinding.ibSmartRopeBack.setOnClickListener {
            FitodyDeviceManager.getInstance().setConnectedDevice(null)
            activity?.onBackPressed()
        }
        setSkipHistoryData()
    }

    override fun onResume() {
        super.onResume()
        setSkipHistoryData()
    }

    private fun setSkipHistoryData() {
        val skipHistoryData = exerciseManagementService.getSkipRopeActivityData()
        viewBinding.tvSmartRopeAverageSkips.text = skipHistoryData.averageSkips.toString()
        viewBinding.tvSmartRopeTotalJumps.text = skipHistoryData.totalSkips.toString()
        viewBinding.tvSmartRopeCaloriesBurned.text = skipHistoryData.totalCaloriesBurned.toString()
        viewBinding.tvSmartRopeTotalTime.text = Utilities.instance.getTimeInHMS(skipHistoryData.totalTimeElapsed).toString()
    }

    fun checkDeviceConnectionStatus(skipMode: Enum.skipRopeActivityMode) {

        if (connectDeviceInfo == null || connectDeviceInfo?.deviceType != Enum.deviceType.skippingRope) {
            val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Fitody")
            alertDialog.setMessage("No device connected, Please connect to a device.")
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener {
                        dialog, which -> dialog.dismiss()
                    val intent = Intent(activity, ConnectDeviceActivity::class.java)
                    resultLauncher.launch(intent)
                })
            alertDialog.show()
        }
        else {
            when (skipMode){
                Enum.skipRopeActivityMode.freeJumpMode -> {
                    val intent = Intent(activity, FreeJumpModeActivity::class.java)
                    intent.putExtra(Constants.connectedDeviceInfoExtra, connectDeviceInfo)
                    skippingActivityResultLauncher.launch(intent)
                }
                Enum.skipRopeActivityMode.targetMode -> {
                    val intent = Intent(activity, JumpingRopeSetTargetActivity::class.java)
                    intent.putExtra(Constants.connectedDeviceInfoExtra, connectDeviceInfo)
                    skippingActivityResultLauncher.launch(intent)
                }
                Enum.skipRopeActivityMode.timerMode -> {
                    val intent = Intent(activity, SkipRopeTimerModeActivity::class.java)
                    intent.putExtra(Constants.connectedDeviceInfoExtra, connectDeviceInfo)
                    skippingActivityResultLauncher.launch(intent)
                }
                Enum.skipRopeActivityMode.countDownMode -> {
                    val intent = Intent(activity, JumpingRopeModeActivity::class.java)
                    intent.putExtra(Constants.jumpModeActivityTypeExtra, Enum.skipRopeActivityMode.countDownMode.value)
                    intent.putExtra(Constants.connectedDeviceInfoExtra, connectDeviceInfo)
                    skippingActivityResultLauncher.launch(intent)
                }
            }
        }

    }
}