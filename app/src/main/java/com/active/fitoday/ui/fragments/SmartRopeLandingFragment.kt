package com.active.fitoday.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitoday.databinding.FragmentSmartRopeLandingBinding
import com.active.fitoday.ui.activity.FreeJumpModeActivity
import com.active.fitoday.ui.activity.JumpingRopeModeActivity
import com.active.fitoday.ui.activity.JumpingRopeSetTargetActivity
import com.active.fitoday.ui.util.Constants
import com.active.fitoday.ui.util.Enum

class SmartRopeLandingFragment: Fragment() {

    lateinit var viewBinding: FragmentSmartRopeLandingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentSmartRopeLandingBinding.inflate(inflater)
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnTargetMode.setOnClickListener {
            val intent = Intent(activity, JumpingRopeSetTargetActivity::class.java)
            startActivity(intent)
        }

        viewBinding.btnCountDown.setOnClickListener {
            val intent = Intent(activity, JumpingRopeModeActivity::class.java)
            intent.putExtra(Constants.jumpModeActivityTypeExtra, Enum.jumpingRopeActivityMode.countDownMode.value)
            startActivity(intent)
        }

        viewBinding.btnFreeJumpMode.setOnClickListener {
            val intent = Intent(activity, FreeJumpModeActivity::class.java)
            startActivity(intent)
        }
    }
}