package com.aztechz.probeez.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aztechz.probeez.model.task.TaskRequestModel
import com.aztechz.probeez.model.task.TaskResponseModel
import com.aztechz.probeez.repository.signup.SignUpRepository
import com.aztechz.probeez.repository.task.TaskRepository
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskViewModel @ViewModelInject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _task: MutableLiveData<DataState<TaskResponseModel>> = MutableLiveData()

    val task: LiveData<DataState<TaskResponseModel>> get() = _task

    fun addTask(taskRequestModel: TaskRequestModel)
    {
        viewModelScope.launch {
           val taskResponseModel = taskRepository.addTask(taskRequestModel)
            taskResponseModel.onEach {
                _task.value = it
            }.launchIn(viewModelScope)
        }
    }
}