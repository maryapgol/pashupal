package com.aztechz.probeez.repository.profile

import android.content.Context
import android.util.Log
import com.aztechz.probeez.model.profile.ProfileResponseModel
import com.aztechz.probeez.model.profile.ProfileUpdateRequest
import com.aztechz.probeez.model.profile.UpdateProfileResponseModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepository(private val context: Context, private val retrofitClient: RetrofitClient) {

    suspend fun getProfileDetails(): Flow<DataState<ProfileResponseModel>> = flow {

        try{
            emit(DataState.Loading)
            val dataProcessor = DataProcessor(context)
          val profileDetails = retrofitClient.getProfileDetails("user/getuserbyid/"+dataProcessor.getStr("user_id"))
            emit(DataState.Success(profileDetails))
        }catch (e: Exception)
        {
            emit(DataState.Error(e))
        }

    }

    suspend fun updateProfile(profileUpdateRequest: ProfileUpdateRequest): Flow<DataState<UpdateProfileResponseModel>> = flow  {
        Log.i("ProfileRepository","On click")

        try{
            emit(DataState.Loading)
            val profileDetails = retrofitClient.updateProfile(profileUpdateRequest)
            emit(DataState.Success(profileDetails))

        }catch (e:java.lang.Exception)
        {
            emit(DataState.Error(e))

        }

    }
}