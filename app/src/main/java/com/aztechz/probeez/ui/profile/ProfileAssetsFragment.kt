package com.aztechz.probeez.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileAssetsBinding
import com.aztechz.probeez.databinding.FragmentProfileProfessionalBinding
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

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileAssetsFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private val profileAssetsFragmentArgs: ProfileAssetsFragmentArgs by navArgs()
    private var gender: String? = null
    private var strFirstName: String? = null
    private var strLastName: String? = null
    private var strDob: String? = null
    private var strMaritalStatus: String? = null
    private var strCity: String? = null
    private var strHobbies: String? = null
    private var about: String? = null
    private var address: String? = null
    private var arrayListInterest: List<String>? = null
    private var strEducation: String? = null
    private var strCurrentWorking: String? = null
    private var strYearExperiance: String? = null
    private var strAchievements: String? = null
    private var strSkills: String? = null
    private var strCertificates: String? = null
    private var strProfessionalIdentity: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProfileAssetsBinding.inflate(inflater, container, false).apply {

            saveNextAssets.setOnClickListener {
                val arrListCertificates: List<String> =
                    strCertificates?.split(",")?.map { it.trim() } ?: ArrayList()
                val arrListHobbies: List<String> =
                    strHobbies?.split(",")?.map { it.trim() } ?: ArrayList()
                val arrListSkills: List<String> =
                    strSkills?.split(",")?.map { it.trim() } ?: ArrayList()
                val dt = activity?.let { it1 -> DataProcessor(it1) }

                val personalDetails = PersonalDetails(about, address, "", strCity, arrayListInterest)
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

            }


        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        about = profileAssetsFragmentArgs.about
        strAchievements = profileAssetsFragmentArgs.achievements
        address = profileAssetsFragmentArgs.address
        strCertificates = profileAssetsFragmentArgs.certificate
        strCity = profileAssetsFragmentArgs.city
        strCurrentWorking = profileAssetsFragmentArgs.currentExp
        strDob = profileAssetsFragmentArgs.dob
        strEducation = profileAssetsFragmentArgs.education
        strFirstName = profileAssetsFragmentArgs.firstname
        gender = profileAssetsFragmentArgs.gender
        strHobbies = profileAssetsFragmentArgs.hobbies
        arrayListInterest = profileAssetsFragmentArgs.interest?.toList()
        strLastName = profileAssetsFragmentArgs.lastname
        strMaritalStatus = profileAssetsFragmentArgs.maritalstatus
        strProfessionalIdentity = profileAssetsFragmentArgs.professionalIdentity
        strSkills = profileAssetsFragmentArgs.skills
        strYearExperiance = profileAssetsFragmentArgs.yearsWorking

        profileViewModel.profileData.observe(viewLifecycleOwner, Observer {

            when (it) {
                is DataState.Loading -> {
                    CustomProgress.showProgress(activity as Context, false)

                }

                is DataState.Success -> {
                    CustomProgress.hideProgress()

                    if (it.data.statusCode == "001") {
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        Snackbar.make(view, it.data?.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                }
            }

        })

    }
}