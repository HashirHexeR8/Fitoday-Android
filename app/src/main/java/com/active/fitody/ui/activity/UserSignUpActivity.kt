package com.active.fitody.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.active.fitody.ServiceLocator
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.ActivityUserSignUpBinding
import com.active.fitody.model.UserDTO
import com.active.fitody.ui.fragments.ChooseUserWeightHeightBottomSheetFragment
import com.active.fitody.ui.fragments.WeightHeightChooserSheetListener
import com.active.fitody.util.Constants
import com.active.fitody.util.Utilities
import java.util.*

class UserSignUpActivity: AppCompatActivity(), WeightHeightChooserSheetListener {

    private lateinit var binding: ActivityUserSignUpBinding

    private val months = arrayOf ("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private val yearsArrayList = ArrayList<String>()
    private val daysList = ArrayList<String>()

    private val minimumYear = 1900

    var userEmail = ""
    var userPassword = ""
    var mUserInfo = UserDTO()

    val mUserManagementService = ServiceLocator.getUserManagementService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        userEmail = intent.getStringExtra(Constants.userEmailExtra) ?: ""
        userPassword = intent.getStringExtra(Constants.userPasswordExtra) ?: ""
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
            if (checkValidation()) {
                if (persistUserData()) {
                    UserPrefs.getInstance().putUserLoggedIn(true)
                    val intent: Intent = Intent (this@UserSignUpActivity, HomeDashboardActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.tvHeightFirst.setOnClickListener {
            ChooseUserWeightHeightBottomSheetFragment.shareInstance(false, this).show(supportFragmentManager, "WeightHeightChooser")
        }

        binding.tvHeightSecond.setOnClickListener {
            ChooseUserWeightHeightBottomSheetFragment.shareInstance(false, this).show(supportFragmentManager, "WeightHeightChooser")
        }

        binding.tvHeightUnit.setOnClickListener {
            ChooseUserWeightHeightBottomSheetFragment.shareInstance(false, this).show(supportFragmentManager, "WeightHeightChooser")
        }

        binding.tvWeightFirst.setOnClickListener {
            ChooseUserWeightHeightBottomSheetFragment.shareInstance(true, this).show(supportFragmentManager, "WeightHeightChooser")
        }

        binding.tvWeightSecond.setOnClickListener {
            ChooseUserWeightHeightBottomSheetFragment.shareInstance(true, this).show(supportFragmentManager, "WeightHeightChooser")
        }

        binding.tvWeightUnit.setOnClickListener {
            ChooseUserWeightHeightBottomSheetFragment.shareInstance(true, this).show(supportFragmentManager, "WeightHeightChooser")
        }
    }

    private fun checkValidation(): Boolean {

        //First Name Text field
        if (binding.edFirstName.text.toString().isEmpty()) {
            Utilities.instance.showSnackBar(binding.root, "Please enter your first name")
            return false
        }

        //Last Name Text field
        if (binding.edLastName.text.toString().isEmpty()) {
            Utilities.instance.showSnackBar(binding.root, "Please enter your second name")
            return false
        }

        //Height text view check
        if (binding.tvHeightFirst.text.toString().toInt() == 0) {
            Utilities.instance.showSnackBar(binding.root, "Please enter your height")
            return false
        }

        //Weight text view check
        if (binding.tvWeightFirst.text.toString().toInt() == 0) {
            Utilities.instance.showSnackBar(binding.root, "Please enter your weight")
            return false
        }

        //Gender Checkbox
        if (binding.rgGender.checkedRadioButtonId < 0) {
            Utilities.instance.showSnackBar(binding.root, "Please select your gender")
            return false
        }

        return true
    }

    private fun persistUserData(): Boolean {
        mUserInfo = UserDTO()
        mUserInfo.userEmail = userEmail
        mUserInfo.userPassword = userPassword
        mUserInfo.userName = binding.edFirstName.text.toString() + binding.edLastName.text.toString()
        mUserInfo.userBirthday = "${months[binding.npMonthPicker.value]} ${binding.npDatePicker.value}, ${yearsArrayList[binding.npYearPicker.value]}"
        mUserInfo.userWeight = "${UserPrefs.getInstance().getUserCurrentWeight()} ${UserPrefs.getInstance().getUserWeightUnit()}"
        mUserInfo.userHeight = "${UserPrefs.getInstance().getUserHeight()} ${UserPrefs.getInstance().getUserHeightUnit()}"
        var selectedRadioButton: RadioButton? = null
        selectedRadioButton = findViewById<RadioButton>(binding.rgGender.checkedRadioButtonId)
        mUserInfo.userGender = selectedRadioButton?.text?.toString() ?: ""
        if (!mUserManagementService.getUserExistStatus(mUserInfo.userEmail)) {
            mUserManagementService.persistUserData(mUserInfo)
            return true
        }
        Utilities.instance.showSnackBar(binding.root, "User email already in exists.")
        return false
    }

    private fun updateDayPicker() {
        val currentSelectedYear = yearsArrayList[binding.npYearPicker.value]
        val currentSelectedMonth = binding.npMonthPicker.value

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, currentSelectedYear.toInt())
        calendar.set(Calendar.MONTH, currentSelectedMonth)
        val numDays: Int = calendar.getActualMaximum(Calendar.DATE)
        daysList.clear()
        for (index in 1..numDays)
        {
            daysList.add(index.toString())
        }
        binding.npDatePicker.minValue = daysList[0].toInt()
        binding.npDatePicker.maxValue = daysList[daysList.size - 1].toInt()
        binding.npDatePicker.wrapSelectorWheel = true
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

    override fun onSheetDismiss(isWeightChooser: Boolean, isSuccess: Boolean) {
        if (isSuccess) {
            if (isWeightChooser) {
                binding.tvWeightFirst.text = UserPrefs.getInstance().getUserCurrentWeight().toString().split(".")[0]
                binding.tvWeightSecond.text = UserPrefs.getInstance().getUserCurrentWeight().toString().split(".")[1]
                binding.tvWeightUnit.text = UserPrefs.getInstance().getUserWeightUnit().toString()
            }
            else {
                binding.tvHeightFirst.text = UserPrefs.getInstance().getUserHeight().toString().split(".")[0]
                binding.tvHeightSecond.text = UserPrefs.getInstance().getUserHeight().toString().split(".")[1]
                binding.tvHeightUnit.text = UserPrefs.getInstance().getUserHeightUnit().toString()
            }
        }
    }
}