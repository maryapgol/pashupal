package com.aztechz.probeez.model

data class SignUpResponseModel(
    val `data`: SignUpResponseData? = null,
    val message: String? = null,
    val status: String? = null,
    val statusCode: String? = null
)
    data class SignUpResponseData(
        val __v: String? = null,
        val _id: String? = null,
        val createDate: String? = null,
        val files: List<Any>? = null,
        val isActive: Boolean? = false,
        val isVerified: Boolean? = false,
        val userId: String? = null
    )
