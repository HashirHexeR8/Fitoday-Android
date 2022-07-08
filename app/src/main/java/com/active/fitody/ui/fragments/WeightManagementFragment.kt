package com.active.fitody.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import cn.icomon.icdevicemanager.model.data.ICWeightData
import cn.icomon.icdevicemanager.model.device.ICDevice
import com.active.fitody.FitodyDeviceHelper
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.ServiceLocator
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.FragmentWeightManagementBinding
import com.active.fitody.databinding.LayoutDailyWeightItemBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.model.UserWeightInfoDTO
import com.active.fitody.ui.activity.ConnectDeviceActivity
import com.active.fitody.ui.activity.UserWeightTargetActivity
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum
import com.active.fitody.util.Utilities
import lecho.lib.hellocharts.model.*
import java.math.RoundingMode
import java.text.DecimalFormat


class WeightManagementFragment(): Fragment(), FitodyDeviceHelper {

    private var isListeningForData: Boolean = false
    lateinit var binding: FragmentWeightManagementBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var weightGoalResultLauncher: ActivityResultLauncher<Intent>
    private var connectDeviceInfo: BluetoothDeviceInfoDTO? = null
    private lateinit var dialog: ProgressDialog

    private var weightManagementService = ServiceLocator.getWeightManagementService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWeightManagementBinding.inflate(inflater)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        FitodyDeviceManager.getInstance().subscribeDeviceUpdates(this@WeightManagementFragment)
    }

    override fun onPause() {
        super.onPause()
        FitodyDeviceManager.getInstance().unsubscribeDeviceUpdates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDeviceInfo = FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.weightScale)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                connectDeviceInfo = result.data?.getParcelableExtra<BluetoothDeviceInfoDTO>(Constants.connectedDeviceInfoExtra)
            }
        }
        weightGoalResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                setWeightHistoryData()
            }
        }

        binding.weightProgressBar.progress = 0F
        binding.tvLostWeight.text = "--"
        binding.tvWightGoalInfo.text = ""
        binding.weightGraphView.visibility = View.GONE

        binding.tvUpdateWeight.setOnClickListener {
            checkForDeviceConnection()
        }

        binding.tvEditGoal.setOnClickListener {
            val activityIntent = Intent(activity, UserWeightTargetActivity::class.java)
            weightGoalResultLauncher.launch(activityIntent)
        }

        binding.ibWeightBack.setOnClickListener {
            FitodyDeviceManager.getInstance().setConnectedDevice(null)
            activity?.onBackPressed()
        }

        binding.ibWeightSettings.setOnClickListener {
//            SelectUnitBottomSheetDialogFragment.shareInstance(Enum.deviceType.weightScale).show(childFragmentManager, "SelectUnit")
        }
        setWeightHistoryData()
    }

    private fun setWeightHistoryData() {
        binding.llDailyWeightInfoItemContainer.removeAllViews()
        val weightHistoryData = weightManagementService.getUserWeightHistory()
        var averageWeight = 0.0
        var count = 1
        var weightData = UserWeightInfoDTO()
        for (weightInfo in weightHistoryData) {
            val view = LayoutDailyWeightItemBinding.inflate(layoutInflater)
            view.tvDayOfWeek.text = weightInfo.dayOfWeek
            view.tvWeight.text = weightInfo.userWeightKg.toString()
            binding.llDailyWeightInfoItemContainer.addView(view.root)
            averageWeight += weightInfo.userWeightKg
            count++
        }

        averageWeight /= count
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        averageWeight = df.format(averageWeight).toDouble()
        binding.tvAverageWeight.text = "$averageWeight Kg"
        val goalType = UserPrefs.getInstance().getUserWeightGoalType()
        if (goalType == Enum.weightGoalType.lose) {
            var weightDifference = UserPrefs.getInstance().getUserWeightTarget() - UserPrefs.getInstance().getUserCurrentWeight()
            if (weightDifference >= 0) {
                binding.tvWightGoalInfo.text = "Weight goal acheived"
            }
            else {
                weightDifference *= -1
                binding.tvWightGoalInfo.text = "Only ${Utilities.instance.roundOffToTwoDigits(weightDifference)} to go to meet your goals"
            }
        }
        else {
            var weightDifference = UserPrefs.getInstance().getUserWeightTarget() - UserPrefs.getInstance().getUserCurrentWeight()
            if (weightDifference <= 0) {
                binding.tvWightGoalInfo.text = "Weight goal acheived"
            }
            else {
                binding.tvWightGoalInfo.text = "Only ${Utilities.instance.roundOffToTwoDigits(weightDifference)} to go to meet your goals"
            }
        }
        val differenceWeight = Math.abs(UserPrefs.getInstance().getUserInitialWeight() - UserPrefs.getInstance().getUserCurrentWeight())
        binding.tvLostWeight.text = differenceWeight.toInt().toString() + UserPrefs.getInstance().getUserWeightUnit()
        binding.weightProgressBar.progress = goalProgress()
        setupChartView()
    }

    private fun goalProgress(): Float {
        val initialGoalDifference = Math.abs(UserPrefs.getInstance().getUserInitialWeight() - UserPrefs.getInstance().getUserWeightTarget())
        val differenceWeight = Math.abs(UserPrefs.getInstance().getUserInitialWeight() - UserPrefs.getInstance().getUserCurrentWeight())
        if (initialGoalDifference > 0) {
            return (differenceWeight / initialGoalDifference) * 100
        }
        return 0f
    }

    private fun setupChartView() {
        val chart = binding.weightGraphView

        val weightHistoryData = weightManagementService.getUserWeightHistory()

        val values: MutableList<PointValue> = ArrayList()
        var count = 1
        weightHistoryData.forEach {
            values.add(PointValue(count.toFloat(), it.userWeightKg.toFloat()))
            count++
        }


        chart.setInteractive(false)
        chart.setZoomType(null)
        chart.setContainerScrollEnabled(false, null)

        val line: Line = Line(values).setColor(Color.parseColor("#EF426F")).setCubic(true)
        line.pointColor = Color.parseColor("#EF426F")
        val lines: MutableList<Line> = ArrayList<Line>()
        lines.add(line)

        val data = LineChartData()
        data.lines = lines
        chart.lineChartData = data

        val axisValuesForX: ArrayList<AxisValue> = ArrayList<AxisValue>()
        val axisValuesForY: ArrayList<AxisValue> = ArrayList<AxisValue>()


        var i = 10f
        for (index in 1..10) {
            axisValuesForX.add(AxisValue(i))
            axisValuesForY.add(AxisValue(i))
            i += 10f
        }

        val axeX = Axis(axisValuesForX)
        axeX.lineColor = Color.BLACK
        axeX.textColor = Color.BLACK
        axeX.name = "Weight"

        val axeY = Axis(axisValuesForX)
        axeY.lineColor = Color.BLACK
        axeY.textColor = Color.BLACK
        axeY.name = "Days"

        data.axisXBottom = axeX
        data.axisYLeft = axeY
        binding.weightGraphView.visibility = View.VISIBLE
    }

    override fun onReceiveWeightData(weightData: ICWeightData) {
        super.onReceiveWeightData(weightData)
        if (weightData.isStabilized && isListeningForData) {
            isListeningForData = false
            UserPrefs.getInstance().putUserCurrentWeight(weightData.weight_kg.toFloat())
            weightManagementService.persistUserWeightData(weightData)
            setWeightHistoryData()
            ReadDeviceDataBottomSheetFragment.shareInstance(Enum.deviceType.weightScale).dismiss()
        }
    }

    private fun checkForDeviceConnection() {
        if (connectDeviceInfo == null || connectDeviceInfo?.deviceType != Enum.deviceType.weightScale) {
            val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Fitody")
            alertDialog.setMessage("No device connected, Please connect to a device.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener {
                        dialog, which -> dialog.dismiss()
                    val intent = Intent(activity, ConnectDeviceActivity::class.java)
                    intent.putExtra(Constants.connectDeviceTypeExtra, Enum.deviceType.weightScale.value)
                    resultLauncher.launch(intent)
                })
            alertDialog.show()
        }
        else {
            isListeningForData = true
            val device = ICDevice()
            device.setMacAddr(connectDeviceInfo?.deviceMacAddress)
            ReadDeviceDataBottomSheetFragment.shareInstance(Enum.deviceType.weightScale).show(childFragmentManager, "ReadDeviceData")
        }
    }

}