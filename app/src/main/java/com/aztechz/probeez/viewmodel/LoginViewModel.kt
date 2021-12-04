package com.aztechz.probeez.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.login.LoginRequestModel
import com.aztechz.probeez.model.login.LoginResponseDataModel
import com.aztechz.probeez.repository.login.LoginRepository
import com.aztechz.probeez.repository.signup.SignUpRepository
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository): ViewModel() {

    private val _login: MutableLiveData<DataState<LoginResponseDataModel>> = MutableLiveData<DataState<LoginResponseDataModel>>()

    val login: MutableLiveData<DataState<LoginResponseDataModel>> get() = _login

     fun login(loginRequestModel: LoginRequestModel)
    {
        viewModelScope.launch {
            loginRepository.login(loginRequestModel).onEach {
                login.value = it

            }.launchIn(viewModelScope)
        }

    }

}