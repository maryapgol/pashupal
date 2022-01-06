package com.aztechz.probeez.repository.task

import com.aztechz.probeez.model.task.TaskListResponseModel
import com.aztechz.probeez.model.task.TaskRequestModel
import com.aztechz.probeez.model.task.TaskResponseModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class TaskRepository constructor(private val restClient: RetrofitClient) {

    suspend fun addTask(taskRequestModel: TaskRequestModel): Flow<DataState<TaskResponseModel>> = flow {
        try {
        emit(DataState.Loading)
        val taskResponseModel = restClient.addTask(taskRequestModel)
        emit(DataState.Success(taskResponseModel))
        }catch(e: Exception){
            emit(DataState.Error(e))

        }
    }

    suspend fun getTaskList(userId: String): Flow<DataState<TaskListResponseModel>> = flow {
        try {
            emit(DataState.Loading)
            val taskResponseModel = restClient.getTaskList("task/getalltask/"+userId)
            emit(DataState.Success(taskResponseModel))
        }catch(e: Exception){
            emit(DataState.Error(e))

        }

    }

}