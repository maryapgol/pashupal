package com.aztechz.probeez.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aztechz.probeez.model.reports.ReportRequestModel
import com.aztechz.probeez.model.reports.ReportResponseModel
import com.aztechz.probeez.repository.ReportRepository
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ReportViewModel @ViewModelInject constructor(private val reportRepository: ReportRepository): ViewModel() {

    private val _report: MutableLiveData<DataState<ResponseBody>> = MutableLiveData<DataState<ResponseBody>>()

    val report: MutableLiveData<DataState<ResponseBody>> get() = _report

     fun getReport(reportRequestModel: ReportRequestModel)
    {
        viewModelScope.launch {
            reportRepository.getReport(reportRequestModel).onEach {
                _report.value = it
            }.launchIn(viewModelScope)
        }

    }
}