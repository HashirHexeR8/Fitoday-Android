package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitody.databinding.FragmentGoalAcheivementPromptBinding

class GoalAcheivedFragment: Fragment() {

    lateinit var viewBinding: FragmentGoalAcheivementPromptBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentGoalAcheivementPromptBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.ibGoalAcheivementBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}