package com.aztechz.probeez.retrofit

import com.aztechz.probeez.model.SignUpResponseData
import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.login.LoginRequestModel
import com.aztechz.probeez.model.login.LoginResponseDataModel
import com.aztechz.probeez.model.profile.ProfileResponseModel
import com.aztechz.probeez.model.profile.ProfileUpdateRequest
import com.aztechz.probeez.model.profile.UpdateProfileResponseModel
import com.aztechz.probeez.model.reports.ReportRequestModel
import com.aztechz.probeez.model.reports.ReportResponseModel
import com.aztechz.probeez.model.signup.SignUpRequest
import com.aztechz.probeez.model.task.TaskListResponseModel
import com.aztechz.probeez.model.task.TaskRequestModel
import com.aztechz.probeez.model.task.TaskResponseModel
import com.aztechz.probeez.model.vendor.AddVendorResponseModel
import com.aztechz.probeez.model.vendor.VendorListResponseModel
import com.aztechz.probeez.repository.vendor.AddVendorRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitClient {

    @POST("user/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponseModel

    @POST("user/login")
    suspend fun login(@Body loginRequestModel: LoginRequestModel): LoginResponseDataModel

    @POST("task/save")
    suspend fun addTask(@Body taskRequestModel: TaskRequestModel): TaskResponseModel

    @POST("vendor/add")
    suspend fun addVendor(@Body addVendorRequestModel: AddVendorRequestModel): AddVendorResponseModel

    @Streaming
    @POST("reports/generatereport")
     fun generateReport(@Body reportRequestModel: ReportRequestModel): Call<ResponseBody>

    @GET()
    suspend fun getTaskList(@Url url: String): TaskListResponseModel

    @GET()
    suspend fun getVendorList(@Url url: String): VendorListResponseModel

    @GET()
    suspend fun getProfileDetails(@Url url: String): ProfileResponseModel

    @Multipart
    @POST("user/update")
    suspend fun updateProfile(@Part image: MultipartBody.Part, @Part("data") data: RequestBody): UpdateProfileResponseModel

    @Multipart
    @POST("user/update")
    suspend fun updateProfile(@Part("data") data: RequestBody): UpdateProfileResponseModel

}