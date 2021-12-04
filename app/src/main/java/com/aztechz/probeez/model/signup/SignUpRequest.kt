package com.aztechz.probeez.model.signup

data class SignUpRequest (
    val fullname: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null
        )