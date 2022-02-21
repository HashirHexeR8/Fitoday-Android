package com.active.fitoday.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.active.fitoday.databinding.FragmentTargetedJumpingModeBinding
import com.active.fitoday.ui.util.Constants
import com.active.fitoday.ui.util.Enum

class JumpingRopeModeActivity: AppCompatActivity() {

    lateinit var viewBinding: FragmentTargetedJumpingModeBinding
    var activityType: Enum.jumpingRopeActivityMode = Enum.jumpingRopeActivityMode.freeJumpMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentTargetedJumpingModeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        activityType = Enum.jumpingRopeActivityMode.enumFromInt(intent?.getIntExtra(Constants.jumpModeActivityTypeExtra, 1) ?: 1)
        when (activityType) {
            Enum.jumpingRopeActivityMode.targetMode -> {
                viewBinding.tvJumpRopeModeTitle.text = "Target Mode"
                viewBinding.tvTargetModeTargetText.text = "Target"
            }
            Enum.jumpingRopeActivityMode.countDownMode -> {
                viewBinding.tvJumpRopeModeTitle.text = "Countdown"
                viewBinding.tvTargetModeTargetText.text = "Ideal"
            }
        }

        viewBinding.ibrlTargetTopBarBack.setOnClickListener {
            finish()
        }

    }

}