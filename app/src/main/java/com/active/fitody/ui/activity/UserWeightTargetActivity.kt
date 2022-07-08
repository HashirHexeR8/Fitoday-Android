package com.active.fitody.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.active.fitody.R
import com.active.fitody.databinding.ActivityUserWeightTargetBinding
import com.active.fitody.ui.fragments.SetWeightGoalFragment

class UserWeightTargetActivity: AppCompatActivity() {

    lateinit var activityViewBinding: ActivityUserWeightTargetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewBinding = ActivityUserWeightTargetBinding.inflate(layoutInflater)
        setContentView(activityViewBinding.root)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, SetWeightGoalFragment(), "SetWeightGoalFragment").commit()
    }
}