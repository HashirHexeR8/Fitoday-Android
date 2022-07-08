package com.active.fitody.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.active.fitody.R
import com.active.fitody.databinding.FragmentExerciseBinding
import com.active.fitody.databinding.LayoutListItemBinding

class ExerciseFragment: Fragment() {
    lateinit var binding: FragmentExerciseBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExerciseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemView1 = LayoutListItemBinding.inflate(layoutInflater, binding.llExerciseListItemContainer, false)
        itemView1.tvItemText.text = "Walking"
        itemView1.tvItemValue.text = "321 Steps"
        itemView1.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_exercise_walking))
        binding.llExerciseListItemContainer.addView(itemView1.root)
        val itemView2 = LayoutListItemBinding.inflate(layoutInflater, binding.llExerciseListItemContainer, false)
        itemView2.tvItemText.text = "Running"
        itemView2.tvItemValue.text = "22 Miles"
        itemView2.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_home_exercise))
        binding.llExerciseListItemContainer.addView(itemView2.root)
        val itemView3 = LayoutListItemBinding.inflate(layoutInflater, binding.llExerciseListItemContainer, false)
        itemView3.tvItemText.text = "Cycling"
        itemView3.tvItemValue.text = "52 Miles"
        itemView3.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_exercise_cycling))
        binding.llExerciseListItemContainer.addView(itemView3.root)
        val itemView4 = LayoutListItemBinding.inflate(layoutInflater, binding.llExerciseListItemContainer, false)
        itemView4.tvItemText.text = "Jumping Rope"
        itemView4.tvItemValue.text = "52 Miles"
        itemView4.ivItemIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_exercise_jump_rope))
        binding.llExerciseListItemContainer.addView(itemView4.root)
        itemView4.root.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.tabFragmentContainer, SmartRopeLandingFragment(), "SmartRopeLandingFragment").addToBackStack("ExerciseFragment").commit()
        }
    }
}