package com.aztechz.probeez.model.profile

data class UpdateProfileResponseModel(
    val `data`: UpdateProfileData,
    val message: String,
    val status: String,
    val statusCode: String
)
    data class UpdateProfileData(
        val __v: Int,
        val _id: String,
        val createDate: String,
        val email: String,
        val files: List<Any>,
        val fullname: String,
        val isActive: Boolean,
        val isVerified: Boolean,
        val password: String,
        val personalDetails: PersonalDetails,
        val phone: Long,
        val professionalDetails: ProfessionalDetails,
        val userId: String
    )


