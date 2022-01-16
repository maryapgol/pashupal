package com.aztechz.probeez.ui.home

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
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentHomeBinding
import com.aztechz.probeez.databinding.FragmentSettingsBinding
import com.aztechz.probeez.ui.login_signup.LoginActivity
import com.aztechz.probeez.ui.profile.ViewProfileActivity
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.utils.Utility
import com.aztechz.probeez.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val TAG = "SettingsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewProfileBtn.typeface = Utility.fontButton
        binding.settingHelp.typeface = Utility.fontMedium
        binding.settingFaq.typeface = Utility.fontRegular
        binding.settingHelp.typeface = Utility.fontRegular
        binding.settingPrivacy.typeface = Utility.fontRegular
        binding.settingTerms.typeface = Utility.fontRegular
        binding.logoutBtn.typeface = Utility.fontMedium

        profileViewModel.profileData.observe(viewLifecycleOwner, Observer {

            when(it)
            {
                is DataState.Loading ->{
                    CustomProgress.showProgress(activity as Context,false)


                }

                is DataState.Success ->{
                    CustomProgress.hideProgress()
                 Log.i(TAG,"Response: "+it.data)
                    if(!it.data.data.isNullOrEmpty())
                    {
                        binding.userDetails.text = it.data.data[0]?.fullname

                    }

                }

                is DataState.Error ->{
                    CustomProgress.hideProgress()
                    Log.i(TAG,"Error: "+it.exception.printStackTrace())


                }

            }

        })

        profileViewModel.getProfileData()

        binding.logoutBtn.setOnClickListener {
            val dataProcess = DataProcessor(it.context)
            dataProcess.clearSharedPreferences()
            val intent = Intent(activity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity?.finish()
        }
        binding.viewProfileBtn.setOnClickListener {
           val intent = Intent(activity, ViewProfileActivity::class.java)
            startActivity(intent)
        }
    }
}