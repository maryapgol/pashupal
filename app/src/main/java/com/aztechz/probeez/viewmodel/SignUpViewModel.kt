package com.aztechz.probeez.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.signup.SignUpRequest
import com.aztechz.probeez.repository.signup.SignUpRepository
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignUpViewModel @ViewModelInject constructor(private val signUpRepository: SignUpRepository): ViewModel() {

    private val _signUp: MutableLiveData<DataState<SignUpResponseModel>> = MutableLiveData<DataState<SignUpResponseModel>>()

    val signUp: MutableLiveData<DataState<SignUpResponseModel>> get() = _signUp

    fun signUp(signUpRequest: SignUpRequest)
    {
        viewModelScope.launch {
             signUpRepository.signUp(signUpRequest).onEach {
                 _signUp.value = it
             }.launchIn(viewModelScope)
        }
    }

}