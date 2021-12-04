package com.aztechz.probeez.repository.signup

import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.signup.SignUpRequest
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SignUpRepository(private val retrofitClient: RetrofitClient) {

   suspend fun signUp(signUpRequest: SignUpRequest): Flow<DataState<SignUpResponseModel>> = flow{
       try {
           emit(DataState.Loading)
           val signUpResponse = retrofitClient.signUp(signUpRequest)
           emit(DataState.Success(signUpResponse))
       }catch(e: Exception){
           emit(DataState.Error(e))

       }
    }
}