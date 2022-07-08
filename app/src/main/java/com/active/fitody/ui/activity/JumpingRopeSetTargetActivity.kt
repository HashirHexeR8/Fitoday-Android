package com.active.fitody.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.active.fitody.databinding.FragmentSetJumpingRopesTargetBinding
import com.active.fitody.model.BluetoothDeviceInfoDTO
import com.active.fitody.util.Constants
import com.active.fitody.util.Enum

class JumpingRopeSetTargetActivity: AppCompatActivity() {

    lateinit var viewBinding: FragmentSetJumpingRopesTargetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentSetJumpingRopesTargetBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val connectedDevice = intent.getParcelableExtra<BluetoothDeviceInfoDTO>(Constants.connectedDeviceInfoExtra)

        viewBinding.tvLetsStart.setOnClickListener {
            finish()
            val sendIntent = Intent(this@JumpingRopeSetTargetActivity, JumpingRopeModeActivity::class.java)
            sendIntent.putExtra(Constants.targetNumberExtra, viewBinding.npTargetPicker.value)
            sendIntent.putExtra(Constants.connectedDeviceInfoExtra, connectedDevice)
            sendIntent.putExtra(Constants.jumpModeActivityTypeExtra, Enum.skipRopeActivityMode.targetMode.value)
            startActivity(sendIntent)
        }

        viewBinding.ibrlTargetTopBarBack.setOnClickListener {
            finish()
        }
    }
}