package com.active.fitoday.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.active.fitoday.databinding.FragmentSetJumpingRopesTargetBinding
import com.active.fitoday.ui.util.Constants
import com.active.fitoday.ui.util.Enum

class JumpingRopeSetTargetActivity: AppCompatActivity() {

    lateinit var viewBinding: FragmentSetJumpingRopesTargetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentSetJumpingRopesTargetBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.tvLetsStart.setOnClickListener {
            finish()
            val intent = Intent(this@JumpingRopeSetTargetActivity, JumpingRopeModeActivity::class.java)
            intent.putExtra(Constants.jumpModeActivityTypeExtra, Enum.jumpingRopeActivityMode.targetMode.value)
            startActivity(intent)
        }

        viewBinding.ibrlTargetTopBarBack.setOnClickListener {
            finish()
        }
    }
}