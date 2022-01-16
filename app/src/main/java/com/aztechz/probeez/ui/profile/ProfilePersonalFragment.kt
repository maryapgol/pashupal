package com.aztechz.probeez.ui.profile

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileImageBinding
import com.aztechz.probeez.databinding.FragmentProfilePersonalBinding
import com.aztechz.probeez.util.SpinnerAdapters
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker


class ProfilePersonalFragment : Fragment() {

    private val profilePersonalFragmentArgs : ProfilePersonalFragmentArgs by navArgs()
    private var about: String? = ""
    private var arralistInterest: List<String>? = ArrayList()
    private var gender: String? = ""
    private var strFirstName: String? = ""
    private var strLastName: String? = ""
    private var strDob: String? = ""
    private var strMaritalStatus: String? = ""
    private var strCity: String? = ""
    private var strHobbies: String? = ""
    private var strAddress: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfilePersonalBinding.inflate(inflater, container, false).apply {

            saveNextPersonal.setOnClickListener {
                strFirstName = firstNameInp.editText?.text.toString()
                strLastName = lastNameInp.editText?.text.toString()
                strDob = dobInput.text.toString()
                strCity = workCityInp.editText?.text.toString()
                strHobbies = hobbiesInp.editText?.text.toString()
                strAddress = address.editText?.text.toString()

                val action = ProfilePersonalFragmentDirections.actionProfilePersonalFragmentToProfileProfessionalFragment(gender.toString(),strFirstName.toString(),strLastName.toString(),strDob.toString(),strMaritalStatus.toString(),strCity.toString(),strHobbies.toString(),about.toString(),
                    arralistInterest?.toTypedArray()!!,
                    strAddress.toString())
                findNavController().navigate(action)
            }

            val adapters = SpinnerAdapters(requireContext())

            etHobbiesValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if(!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(),chipGroupHobbies)
                        etHobbiesValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            maritalStatusSpinner.adapter = adapters.maritalStatusAdapter
            maritalStatusSpinner.setSelection(0)
            maritalStatusSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                    strMaritalStatus = adapters.maritalStatusAdapter.getItem(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

            maleButton.setOnClickListener {
                gender = "Male"
                maleButton.setBackgroundColor(Color.parseColor("#f9aa33"))
                femaleButton.setBackgroundColor(Color.parseColor("#00000000"))
            }

            femaleButton.setOnClickListener {
                gender = "Female"
                femaleButton.setBackgroundColor(Color.parseColor("#f9aa33"))
                maleButton.setBackgroundColor(Color.parseColor("#00000000"))
            }

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Date of birth")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            dobInput.setOnClickListener {
                datePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            }

            datePicker.addOnPositiveButtonClickListener {
                dobInput.text = datePicker.headerText
            }



        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        about = profilePersonalFragmentArgs.about
        arralistInterest = profilePersonalFragmentArgs.interests.toList()
    }

    private fun addChipToGroup(chipText: String, chipGroup: ChipGroup) {

        val chip = Chip(requireContext())
        chip.setChipDrawable(ChipDrawable.createFromAttributes(requireContext(),null,0, R.style.Widget_MaterialComponents_Chip_Entry))
        chip.text = chipText
        // chip.chipIcon = ContextCompat.getDrawable(requireContext(), baseline_person_black_18)
        chip.isCloseIconVisible = true
        //chip.setChipIconTintResource(R.color.chipIconTint)
        // necessary to get single selection working
        chip.isClickable = false
        chip.isCheckable = false
        chipGroup.addView(chip as View)
        chip.setOnCloseIconClickListener { chipGroup.removeView(chip as View) }
    }

}