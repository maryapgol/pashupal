package com.aztechz.probeez.repository.login

import com.aztechz.probeez.model.login.LoginRequestModel
import com.aztechz.probeez.model.login.LoginResponseDataModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoginRepository(private val retrofitClient: RetrofitClient) {

    suspend fun login(loginRequestModel: LoginRequestModel): Flow<DataState<LoginResponseDataModel>> =
        flow {
            try {
                emit(DataState.Loading)
                val response = retrofitClient.login(loginRequestModel)
                print("Log repoitory: "+response)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e))

            }
        }


}