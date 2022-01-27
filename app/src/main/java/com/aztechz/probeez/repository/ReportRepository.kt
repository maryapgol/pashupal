package com.aztechz.probeez.repository

import android.content.Context
import com.aztechz.probeez.model.reports.ReportRequestModel
import com.aztechz.probeez.model.reports.ReportResponseModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class ReportRepository(private val context: Context, private val retrofitClient: RetrofitClient) {


    fun getReport(reportRequestModel: ReportRequestModel): Flow<DataState<ResponseBody>> = flow {

        try {
            emit(DataState.Loading)
            val responseBody = retrofitClient.generateReport(reportRequestModel)
            responseBody.execute()
           print("REspone body: "+responseBody)


        } catch (e: Exception) {
            emit(DataState.Error(e))

        }

    }
}