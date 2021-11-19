package com.aztechz.probeez.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileProfessionalBinding
import com.aztechz.probeez.util.SpinnerAdapters
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup


class ProfileProfessionalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProfileProfessionalBinding.inflate(inflater, container, false).apply {

            saveNextProfessional.setOnClickListener {
                findNavController().navigate(R.id.action_profileProfessionalFragment_to_profileProfessionalCategoryFragment)
            }

            val adapters = SpinnerAdapters(requireContext())
            etAchievementValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if(!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(),chipGroupAchievements)
                        etAchievementValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            etSkillValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if(!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(),chipGroupSkill)
                        etSkillValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            etcoursesValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if(!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(),chipGroupCourses)
                        etcoursesValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            educationSpinner.adapter = adapters.highestEducationAdapter
            educationSpinner.setSelection(0)

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