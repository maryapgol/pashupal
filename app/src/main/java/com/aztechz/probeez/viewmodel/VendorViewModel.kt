package com.aztechz.probeez.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aztechz.probeez.model.task.TaskRequestModel
import com.aztechz.probeez.model.task.TaskResponseModel
import com.aztechz.probeez.model.vendor.AddVendorResponseModel
import com.aztechz.probeez.repository.task.TaskRepository
import com.aztechz.probeez.repository.vendor.AddVendorRequestModel
import com.aztechz.probeez.repository.vendor.VendorRepository
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class VendorViewModel @ViewModelInject constructor(private val vendorRepository: VendorRepository): ViewModel() {

    private val _vendor: MutableLiveData<DataState<AddVendorResponseModel>> = MutableLiveData()

    val vendor: LiveData<DataState<AddVendorResponseModel>> get() = _vendor


    fun addVendor(addVendorRequestModel: AddVendorRequestModel)
    {
        viewModelScope.launch {
            val vendorResponseModel = vendorRepository.addVendor(addVendorRequestModel)
            vendorResponseModel.onEach {
                _vendor.value = it
            }.launchIn(viewModelScope)
        }
    }

}