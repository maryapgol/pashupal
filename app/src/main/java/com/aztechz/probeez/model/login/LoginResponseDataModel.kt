package com.aztechz.probeez.model.login

data class LoginResponseDataModel(
    val `data`: LoginResponseData? = null,
    val message: String? = null,
    val status: String? = null,
    val statusCode: String? = null
)
    data class LoginResponseData(
        val email: String? = null,
        val phone: String? = null,
        val userId: String? = null
    )
