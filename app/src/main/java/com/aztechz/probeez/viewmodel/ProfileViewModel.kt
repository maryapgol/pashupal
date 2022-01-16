package com.aztechz.probeez.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aztechz.probeez.model.profile.ProfileResponseModel
import com.aztechz.probeez.model.profile.ProfileUpdateRequest
import com.aztechz.probeez.model.profile.UpdateProfileResponseModel
import com.aztechz.probeez.repository.profile.ProfileRepository
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(private val profileRepository : ProfileRepository): ViewModel() {

    private val _profileData: MutableLiveData<DataState<ProfileResponseModel>> = MutableLiveData()
    private val _updateprofileData: MutableLiveData<DataState<UpdateProfileResponseModel>> = MutableLiveData()

    val profileData : LiveData<DataState<ProfileResponseModel>> get() = _profileData
    val updateprofile : LiveData<DataState<UpdateProfileResponseModel>> get() = _updateprofileData

    fun getProfileData()
    {
        viewModelScope.launch {

            profileRepository.getProfileDetails().onEach {
                _profileData.value = it
            }.launchIn(viewModelScope)

        }
    }

    fun updateProfileDate(profileUpdateRequest: ProfileUpdateRequest)
    {
          viewModelScope.launch {
              Log.i("ProfileViewModel","On click")

              profileRepository.updateProfile(profileUpdateRequest).onEach {
                  _updateprofileData.value = it

              }.launchIn(viewModelScope)

          }
    }
}