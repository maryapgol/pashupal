package com.aztechz.probeez.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfilePersonalBinding
import com.aztechz.probeez.databinding.FragmentProfileProfessionalCategoryBinding
import com.aztechz.probeez.model.profile.PersonalDetails
import com.aztechz.probeez.model.profile.ProfessionalDetails
import com.aztechz.probeez.model.profile.ProfileUpdateRequest
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


class ProfileProfessionalCategoryFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private val profileProfessionalCategoryFragmentArgs: ProfileProfessionalCategoryFragmentArgs by navArgs()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding =
            FragmentProfileProfessionalCategoryBinding.inflate(inflater, container, false).apply {

                addMoreProfessional.setOnClickListener {
                    //findNavController().navigate(R.id.action_profileProfessionalCategoryFragment_self)
                }

                saveNextProfessionalCategory.setOnClickListener {
                    val arrListCertificates: List<String> =
                        strCertificates?.split(",")?.map { it.trim() } ?: ArrayList()
                    val arrListHobbies: List<String> =
                        strHobbies?.split(",")?.map { it.trim() } ?: ArrayList()
                    val arrListSkills: List<String> =
                        strSkills?.split(",")?.map { it.trim() } ?: ArrayList()
                    val dt = activity?.let { it1 -> DataProcessor(it1) }

                    val personalDetails = PersonalDetails(about, address, "", strCity, arralistInterest)
                    val professionalDetails =
                        ProfessionalDetails(arrListCertificates, arrListHobbies, arrListSkills)
                    val profileUpdateRequest = ProfileUpdateRequest(
                        dt?.getStr("email").toString(),
                        strFirstName + " " + strLastName,
                        personalDetails,
                        dt?.getStr("phone").toString(),
                        professionalDetails,
                        dt?.getStr("user_id").toString(),
                        ""
                    )
                    profileViewModel.updateProfileDate(profileUpdateRequest)
                        /*val action = ProfileProfessionalCategoryFragmentDirections.actionProfileProfessionalCategoryFragmentToProfileAssetsFragment(
                                gender.toString(),
                                strFirstName.toString(),
                                strLastName.toString(),
                                strDob.toString(),
                                strMaritalStatus.toString(),
                                strCity.toString(),
                                strHobbies.toString(),
                                about.toString(),
                                arralistInterest?.toTypedArray()!!,
                                address.toString(),
                                strEducation.toString(),
                                strYearExperiance.toString(),
                                strCurrentWorking.toString(),
                                strAchievements.toString(),
                                strSkills.toString(),
                                strCertificates.toString(),
                                strProfessionalIdentity.toString()
                            )
                        findNavController().navigate(action)*/

                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        about = profileProfessionalCategoryFragmentArgs.about
        strAchievements = profileProfessionalCategoryFragmentArgs.achievements
        address = profileProfessionalCategoryFragmentArgs.address
        strCertificates = profileProfessionalCategoryFragmentArgs.certificate
        strCity = profileProfessionalCategoryFragmentArgs.city
        strCurrentWorking = profileProfessionalCategoryFragmentArgs.currentExp
        strDob = profileProfessionalCategoryFragmentArgs.dob
        strEducation = profileProfessionalCategoryFragmentArgs.education
        strFirstName = profileProfessionalCategoryFragmentArgs.firstname
        gender = profileProfessionalCategoryFragmentArgs.gender
        strHobbies = profileProfessionalCategoryFragmentArgs.hobbies
        arralistInterest = profileProfessionalCategoryFragmentArgs.interest?.toList()
        strLastName = profileProfessionalCategoryFragmentArgs.lastname
        strMaritalStatus = profileProfessionalCategoryFragmentArgs.maritalstatus
        strProfessionalIdentity = profileProfessionalCategoryFragmentArgs.professionalIdentity
        strSkills = profileProfessionalCategoryFragmentArgs.skills
        strYearExperiance = profileProfessionalCategoryFragmentArgs.yearsWorking

    }
}