package com.active.fitody.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.callback.ICScanDeviceDelegate
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.device.ICScanDeviceInfo
import cn.icomon.icdevicemanager.model.other.ICConstant
import com.active.fitody.FitodyDeviceManager
import com.active.fitody.databinding.ActivityConnectDeviceBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.ui.adapter.ConnectDeviceRecyclerViewAdapter
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum


class ConnectDeviceActivity: AppCompatActivity(), ICScanDeviceDelegate {

    lateinit var viewBinding: ActivityConnectDeviceBinding

    private var deviceList: ArrayList<BluetoothDeviceInfoDTO> = ArrayList()
    private var deviceListAdapter = ConnectDeviceRecyclerViewAdapter { selectedDevice: BluetoothDeviceInfoDTO -> onDeviceSelected(selectedDevice) }
    private var deviceType = Enum.deviceType.skippingRope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityConnectDeviceBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        deviceType = Enum.deviceType.enumFromInt(intent.getIntExtra(Constants.connectDeviceTypeExtra, 1))
        ICDeviceManager.shared().scanDevice(this)
        viewBinding.rvDeviceList.layoutManager = LinearLayoutManager(this)
        viewBinding.rvDeviceList.adapter = deviceListAdapter
        deviceListAdapter.setDataSource(deviceList)
        viewBinding.ibNewDeviceBack.setOnClickListener {
            finish()
        }
        checkForPermissions()
    }

    private fun checkForPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this@ConnectDeviceActivity, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission( this@ConnectDeviceActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val alertDialog: AlertDialog = AlertDialog.Builder(this@ConnectDeviceActivity).create()
            alertDialog.setTitle("Fitody")
            alertDialog.setMessage("Fitody requires location permissions to discover nearby devices.")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                        ),
                        99
                    )
                })
            alertDialog.show()
        }

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {

        }
        else if (!mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.enable()
        }
        else {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkForPermissions()
    }

    override fun onScanResult(newDevice: ICScanDeviceInfo) {
        when (deviceType) {
            Enum.deviceType.skippingRope -> {
                if (newDevice.type == ICConstant.ICDeviceType.ICDeviceTypeSkip) {
                    val device = BluetoothDeviceInfoDTO(newDevice)
                    if (!deviceList.contains(device)) {
                        deviceListAdapter.addToDataSource(device)
                    }
                }
            }
            Enum.deviceType.weightScale -> {
                if (newDevice.type == ICConstant.ICDeviceType.ICDeviceTypeFatScale) {
                    val device = BluetoothDeviceInfoDTO(newDevice)
                    if (!deviceList.contains(device)) {
                        deviceListAdapter.addToDataSource(device)
                    }
                }
            }
            else -> {
                if (newDevice.type == ICConstant.ICDeviceType.ICDeviceTypeRuler) {
                    val device = BluetoothDeviceInfoDTO(newDevice)
                    if (!deviceList.contains(device)) {
                        deviceListAdapter.addToDataSource(device)
                    }
                }
            }
        }
    }

    private fun onDeviceSelected(selectedDevice: BluetoothDeviceInfoDTO) {

        val connectedDevice = FitodyDeviceManager.getInstance().getConnectedDevice(Enum.deviceType.all)
        if (connectedDevice != null) {
            val device = ICDevice()
            device.setMacAddr(connectedDevice.deviceMacAddress)
            ICDeviceManager.shared().removeDevice(device, ICConstant.ICRemoveDeviceCallBack { icDevice, icRemoveDeviceCallBackCode -> })
        }

        val device = ICDevice()
        device.setMacAddr(selectedDevice.deviceMacAddress)

        ICDeviceManager.shared().addDevice(device, ICConstant.ICAddDeviceCallBack { icDevice, icAddDeviceCallBackCode ->
            FitodyDeviceManager.getInstance().setConnectedDevice(selectedDevice)
            val intent = Intent()
            intent.putExtra(Constants.connectedDeviceInfoExtra, selectedDevice)
            setResult(RESULT_OK, intent)
            finish()
        })

    }

}