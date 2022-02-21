package com.active.fitoday.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.active.fitoday.databinding.ActivityUserSignUpBinding
import java.util.*

class UserSignUpActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUserSignUpBinding

    private val months = arrayOf ("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private val yearsArrayList = ArrayList<String>()
    private val daysList = ArrayList<String>()

    private val minimumYear = 1900

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Year Picker
        updateYearPicker()

        //Month Picker
        updateMonthPicker()

        //Day Picker
        updateDayPicker()

        binding.btnDateDown.setOnClickListener {
            val selectedValue = binding.npDatePicker.value
            binding.npDatePicker.value = selectedValue + 1
        }

        binding.btnDateUp.setOnClickListener {
            val selectedValue = binding.npDatePicker.value
            binding.npDatePicker.value = selectedValue - 1
        }

        binding.btnMonthDown.setOnClickListener {
            val selectedValue = binding.npMonthPicker.value
            binding.npMonthPicker.value = selectedValue + 1
        }

        binding.btnMonthUp.setOnClickListener {
            val selectedValue = binding.npMonthPicker.value
            binding.npMonthPicker.value = selectedValue - 1
        }

        binding.btnYearDown.setOnClickListener {
            val selectedValue = binding.npYearPicker.value
            binding.npYearPicker.value = selectedValue + 1
        }

        binding.btnYearUp.setOnClickListener {
            val selectedValue = binding.npYearPicker.value
            binding.npYearPicker.value = selectedValue - 1
        }

        binding.npYearPicker.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            updateMonthPicker()
        }

        binding.npMonthPicker.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            updateDayPicker()
        }

        binding.btnCreateAccount.setOnClickListener {
            val intent: Intent = Intent (this@UserSignUpActivity, HomeDashboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDayPicker() {
        val currentSelectedYear = yearsArrayList[binding.npYearPicker.value]
        val currentSelectedMonth = binding.npMonthPicker.value

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, currentSelectedYear.toInt())
        calendar.set(Calendar.MONTH, currentSelectedMonth)
        val numDays: Int = calendar.getActualMaximum(Calendar.DATE)
        for (index in 1..numDays)
        {
            daysList.add(index.toString())
        }
        binding.npDatePicker.minValue = 0
        binding.npDatePicker.maxValue = daysList.size-1
        binding.npDatePicker.wrapSelectorWheel = true
        binding.npDatePicker.displayedValues = daysList.toTypedArray()
    }

    private fun updateMonthPicker() {
        binding.npMonthPicker.minValue = 0
        binding.npMonthPicker.maxValue = months.size-1
        binding.npMonthPicker.wrapSelectorWheel = true
        binding.npMonthPicker.displayedValues = months
    }

    private fun updateYearPicker() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        for (index in minimumYear..currentYear)
        {
            yearsArrayList.add(index.toString())
        }
        binding.npYearPicker.minValue = 0
        binding.npYearPicker.maxValue = yearsArrayList.size-1
        binding.npYearPicker.wrapSelectorWheel = true
        binding.npYearPicker.displayedValues = yearsArrayList.toTypedArray()
    }
}