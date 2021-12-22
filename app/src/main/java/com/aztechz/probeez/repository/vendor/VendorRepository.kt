package com.aztechz.probeez.repository.vendor

import com.aztechz.probeez.model.vendor.AddVendorResponseModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class VendorRepository constructor(private val restClient: RetrofitClient) {

    suspend fun addVendor(addVendorRequestModel: AddVendorRequestModel): Flow<DataState<AddVendorResponseModel>> = flow {
        try {
            emit(DataState.Loading)
            val addVendorResponseModel = restClient.addVendor(addVendorRequestModel)
            emit(DataState.Success(addVendorResponseModel))
        }catch(e: Exception){
            emit(DataState.Error(e))

        }
    }

}