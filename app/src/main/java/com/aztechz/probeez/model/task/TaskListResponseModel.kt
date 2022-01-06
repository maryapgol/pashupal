package com.aztechz.probeez.model.task

data class TaskListResponseModel(
    val `data`: List<TaskListResponseData?>? = null,
    val message: String? = null,
    val statusCode: String? = null
)
    data class TaskListResponseData(
        val __v: Int? = 0,
        val _id: String? = null,
        val createDate: String? = null,
        val description: String? = null,
        val taskDate: String? = null,
        val taskTime: Any? = null,
        val title: String? = null,
        val type: String? = null,
        val userId: String? = null,
        val vendorId: String? = null
    )
