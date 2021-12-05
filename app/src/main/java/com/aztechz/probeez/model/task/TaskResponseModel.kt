package com.aztechz.probeez.model.task

data class TaskResponseModel(
    val `data`: TaskResponseData ?= null,
    val message: String ?= null,
    val statusCode: String ?= null
)
    data class TaskResponseData(
        val __v: Int ?= null,
        val _id: String ?= null,
        val createDate: String ?= null,
        val description: String ?= null,
        val taskDate: String ?= null,
        val taskTime: String ?= null,
        val title: String ?= null,
        val type: String ?= null,
        val userId: String ?= null,
        val vendorId: String ?= null
    )
