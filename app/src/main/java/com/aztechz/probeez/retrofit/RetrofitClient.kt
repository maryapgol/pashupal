package com.aztechz.probeez.retrofit

import com.aztechz.probeez.model.SignUpResponseData
import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.login.LoginRequestModel
import com.aztechz.probeez.model.login.LoginResponseDataModel
import com.aztechz.probeez.model.signup.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitClient {

    @POST("user/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponseModel

    @POST("user/login")
    suspend fun login(@Body loginRequestModel: LoginRequestModel): LoginResponseDataModel

}