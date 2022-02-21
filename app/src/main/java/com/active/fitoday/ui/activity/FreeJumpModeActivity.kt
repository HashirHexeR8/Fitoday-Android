package com.active.fitoday.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.active.fitoday.databinding.FragmentFreeJumpModeBinding
import com.active.fitoday.databinding.FragmentSetJumpingRopesTargetBinding
import com.active.fitoday.ui.util.Constants
import com.active.fitoday.ui.util.Enum

class FreeJumpModeActivity:AppCompatActivity() {
    lateinit var viewBinding: FragmentFreeJumpModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentFreeJumpModeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.ibFreeJumpModeTopBarBack.setOnClickListener {
            finish()
        }
    }
}