package com.aztechz.probeez.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileProfessionalBinding
import com.aztechz.probeez.model.profile.PersonalDetails
import com.aztechz.probeez.model.profile.ProfessionalDetails
import com.aztechz.probeez.model.profile.ProfileData
import com.aztechz.probeez.model.profile.ProfileUpdateRequest
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.util.SpinnerAdapters
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.utils.Utility
import com.aztechz.probeez.viewmodel.ProfileViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile_professional.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileProfessionalFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private val profileProfessionalFragmentArgs: ProfileProfessionalFragmentArgs by navArgs()
    private var gender: String? = ""
    private var strFirstName: String? = ""
    private var strLastName: String? = ""
    private var strDob: String? = ""
    private var strMaritalStatus: String? = ""
    private var strCity: String? = ""
    private var strHobbies: String? = ""
    private var about: String? = ""
    private var address: String? = ""
    private var arralistInterest: List<String>? = ArrayList()
    private var strEducation: String? = ""
    private var strCurrentWorking: String? = ""
    private var strYearExperiance: String? = ""
    private var strAchievements: String? = ""
    private var strSkills: String? = ""
    private var strCertificates: String? = ""
    private var strProfessionalIdentity: String? = ""
    private var profileData: ProfileData? = null
    private var strFilePath: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProfileProfessionalBinding.inflate(inflater, container, false).apply {

            saveNextProfessional.setOnClickListener {

                strCertificates = coursesInp.editText?.text.toString()
                strSkills = skillsInp.editText?.text.toString()
                val arrListCertificates: List<String> =
                    strCertificates?.split(",")?.map { it.trim() } ?: ArrayList()
                val arrListHobbies: List<String> =
                    strHobbies?.split(",")?.map { it.trim() } ?: ArrayList()
                val arrListSkills: List<String> =
                    strSkills?.split(",")?.map { it.trim() } ?: ArrayList()
                val dt = activity?.let { it1 -> DataProcessor(it1) }

                val personalDetails = PersonalDetails(
                    about,
                    address,
                    "",
                    strCity,
                    arralistInterest,
                    "",
                    "",
                    gender,
                    strDob
                )
                val professionalDetails =
                    ProfessionalDetails(
                        arrListCertificates,
                        arrListHobbies,
                        arrListSkills,
                        ArrayList<String>(),
                        strEducation,
                        strCurrentWorking,
                        strAchievements,
                        strProfessionalIdentity,
                        strYearExperiance
                    )
                val profileUpdateRequest = ProfileUpdateRequest(
                    dt?.getStr("email").toString(),
                    strFirstName + " " + strLastName,
                    personalDetails,
                    dt?.getStr("phone").toString(),
                    professionalDetails,
                    dt?.getStr("user_id").toString(),
                    strFilePath.toString()
                )
                Log.i("ProfileProfessionalFragment", "On click")
                profileViewModel.updateProfileDate(profileUpdateRequest)
                /*    strCurrentWorking = workingInp.editText.toString()
                    strYearExperiance = yearsExpInp.editText.toString()
                    strAchievements = achievementsInp.editText.toString()
                    strSkills = skillsInp.editText.toString()
                    strCertificates = coursesInp.editText.toString()
                    strProfessionalIdentity = professionalInp.editText.toString()
                    ProfileProfessionalFragmentDirections.actionProfileProfessionalFragmentToProfileProfessionalCategoryFragment(gender.toString(),strFirstName.toString(),strLastName.toString(),strDob.toString(),strMaritalStatus.toString(),strCity.toString(),strHobbies.toString(),about.toString(),arralistInterest?.toTypedArray()!!,address.toString(),strEducation.toString(),strYearExperiance.toString(),strCurrentWorking.toString(),strAchievements.toString(),strSkills.toString(),strCertificates.toString(),strProfessionalIdentity.toString())
                    findNavController().navigate(R.id.action_profileProfessionalFragment_to_profileProfessionalCategoryFragment)*/
            }

            skip.setOnClickListener {
                saveNextProfessional.performClick()
            }

            val adapters = SpinnerAdapters(requireContext())
            etAchievementValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if (!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(), chipGroupAchievements)
                        etAchievementValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            etSkillValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if (!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(), chipGroupSkill)
                        etSkillValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            etcoursesValue.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val txtVal = v.text
                    if (!txtVal.isNullOrEmpty()) {
                        addChipToGroup(txtVal.toString(), chipGroupCourses)
                        etcoursesValue.setText("")
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            educationSpinner.adapter = adapters.highestEducationAdapter
            educationSpinner.setSelection(0)
            educationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    strEducation = adapters.highestEducationAdapter.getItem(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
            pdTitleText.typeface = Utility.fontBold
            pdSubtitleText.typeface = Utility.fontRegular
            workingInp.typeface = Utility.fontRegular
            edtWorking.typeface = Utility.fontRegular
            yearsExpInp.typeface = Utility.fontRegular
            edtYearsExp.typeface = Utility.fontRegular
            achievementsInp.typeface = Utility.fontRegular
            etAchievementValue.typeface = Utility.fontRegular
            etSkillValue.typeface = Utility.fontRegular
            skillsInp.typeface = Utility.fontRegular
            etcoursesValue.typeface = Utility.fontRegular
            coursesInp.typeface = Utility.fontRegular
            professionalInp.typeface = Utility.fontRegular
            edtProfessional.typeface = Utility.fontRegular
            skip.typeface = Utility.fontButton
            saveNextProfessional.typeface = Utility.fontButton
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gender = profileProfessionalFragmentArgs.gender
        strCity = profileProfessionalFragmentArgs.city
        strDob = profileProfessionalFragmentArgs.dob
        strFirstName = profileProfessionalFragmentArgs.firstname
        strHobbies = profileProfessionalFragmentArgs.hobbies
        strLastName = profileProfessionalFragmentArgs.lastname
        strMaritalStatus = profileProfessionalFragmentArgs.maritalstatus
        about = profileProfessionalFragmentArgs.about
        arralistInterest = profileProfessionalFragmentArgs.interest.toList()
        address = profileProfessionalFragmentArgs.address
        profileData = profileProfessionalFragmentArgs.profileData
        strFilePath = profileProfessionalFragmentArgs.profileImage
        setProfileData(profileData)
        profileViewModel.updateprofile.observe(viewLifecycleOwner, Observer {
            Log.i("ProfileProfessional", "Status Code: ")
            when (it) {
                is DataState.Loading -> {
                    CustomProgress.showProgress(activity as Context, false)

                }

                is DataState.Success -> {
                    CustomProgress.hideProgress()
                    Log.i("ProfileProfessional", "Status Code: " + it.data.statusCode)

                    if (it.data.statusCode == "001") {
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        activity?.startActivity(intent)
                        activity?.finish()
                    } else {
                        Snackbar.make(view, it.data?.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        activity?.startActivity(intent)
                        activity?.finish()
                    }
                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()
                    Log.i("ProfileProfessional", "ERROR: " + it.exception.printStackTrace())
                }
            }

        })

    }

    private fun setProfileData(profileData: ProfileData?) {
        if (profileData != null) {
            workingInp.editText?.setText("")
            edtYearsExp.setText("")
            et_achievement_value.setText("")
            if (!profileData.professionalDetails?.skills.isNullOrEmpty()) {
                etSkillValue.setText(profileData.professionalDetails?.skills?.joinToString(separator = ","))
            }
            if (!profileData.professionalDetails?.certificates.isNullOrEmpty()) {
                etcoursesValue?.setText(
                    profileData.professionalDetails?.certificates?.joinToString(
                        separator = ","
                    )
                )
            }

            if (!profileData.professionalDetails?.qualification.isNullOrEmpty()) {
                when (profileData.professionalDetails?.qualification) {
                    "Qualification" -> {
                        educationSpinner.setSelection(1)
                    }
                    "Uneducated" -> {
                        educationSpinner.setSelection(2)

                    }
                    "Pre School / SSC" -> {
                        educationSpinner.setSelection(3)

                    }
                    "Under Graduate" -> {
                        educationSpinner.setSelection(4)

                    }
                    "Graduate" -> {
                        educationSpinner.setSelection(5)

                    }
                    "Post Graduate" -> {
                        educationSpinner.setSelection(6)

                    }
                    "Professional Graduate" -> {
                        educationSpinner.setSelection(7)

                    }
                    "Medical Professional Graduate" -> {
                        educationSpinner.setSelection(8)

                    }


                }
            }
            if (!profileData.professionalDetails?.current_work.isNullOrEmpty())
            {
                edtWorking.setText(profileData.professionalDetails?.current_work)
            }
            if (!profileData.professionalDetails?.experience.isNullOrEmpty())
            {
                edtYearsExp.setText(profileData.professionalDetails?.experience)
            }
            if (!profileData.professionalDetails?.achievements.isNullOrEmpty())
            {
                et_achievement_value.setText(profileData.professionalDetails?.achievements)
            }
            if (!profileData.professionalDetails?.identity.isNullOrEmpty())
            {
                edtProfessional.setText(profileData.professionalDetails?.identity)
            }



        }
    }

    private fun addChipToGroup(chipText: String, chipGroup: ChipGroup) {

        val chip = Chip(requireContext())
        chip.setChipDrawable(
            ChipDrawable.createFromAttributes(
                requireContext(),
                null,
                0,
                R.style.Widget_MaterialComponents_Chip_Entry
            )
        )
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