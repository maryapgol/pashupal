package com.aztechz.probeez.model.profile

data class ProfileUpdateRequest(
    val email: String,
    val fullname: String,
    val personalDetails: PersonalDetails,
    val phone: String,
    val professionalDetails: ProfessionalDetails,
    val userId: String
)

