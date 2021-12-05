package com.aztechz.probeez.model.task

data class TaskRequestModel(
    val description: String,
    val taskDate: String,
    val taskTime: String,
    val title: String,
    val type: String,
    val userId: String,
    val vendorId: String
)