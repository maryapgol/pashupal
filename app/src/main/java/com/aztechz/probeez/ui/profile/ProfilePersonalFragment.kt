package com.aztechz.probeez.ui.profile

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileImageBinding
import com.aztechz.probeez.databinding.FragmentProfilePersonalBinding
import com.aztechz.probeez.util.SpinnerAdapters
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker


class ProfilePersonalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfilePersonalBinding.inflate(inflater, container, false).apply {

            saveNextPersonal.setOnClickListener {
                findNavController().navigate(R.id.action_profilePersonalFragment_to_profileProfessionalFragment)
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

            maleButton.setOnClickListener {
                maleButton.setBackgroundColor(Color.parseColor("#f9aa33"))
                femaleButton.setBackgroundColor(Color.parseColor("#00000000"))
            }

            femaleButton.setOnClickListener {
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