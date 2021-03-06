package com.aztechz.probeez.model.profile

import java.io.Serializable

data class ProfileResponseModel(
    val `data`: List<ProfileData?>? = null,
    val message: String? = null,
    val statusCode: String? = null
)
    data class ProfileData(
        val profilePic: String? = null,
        val __v: Int? = 0,
        val _id: String? = null,
        val createDate: String? = null,
        val email: String? = null,
        val files: List<String>? = null,
        val fullname: String? = null,
        val isActive: Boolean? = false,
        val isVerified: Boolean? = false,
        val password: String? = null,
        val personalDetails: PersonalDetails? = null,
        val phone: Long? = 0L,
        val professionalDetails: ProfessionalDetails? = null,
        val userId: String? = null,
        val dob: String? = null
    ): Serializable
        data class PersonalDetails(
            val about: String? = null,
            val addressLine1: String? = null,
            val addressLine2: String? = null,
            val city: String? = null,
            val intersts: List<String?>? = null,
            val pincode: String? = null,
            val state: String? = null,
            val gender: String? = null,
            val dob: String? = null

        ): Serializable

        data class ProfessionalDetails(
            val certificates: List<String?>? = null,
            val hobbies: List<String?>? = null,
            val skills: List<String?>? = null,
            val testimonials: List<String?>? = null,
            val qualification: String?= null,
            val current_work: String? = null,
            val achievements: String? = null,
            val identity: String? = null,
            val experience: String? = null,

            ): Serializable

